package com.bangmodteam.workshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.constant.TicketStatus;
import com.bangmodteam.workshop.dto.TicketDetailDTO;
import com.bangmodteam.workshop.dto.UserDetailDTO;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.entity.Ticket;
import com.bangmodteam.workshop.entity.User;
import com.bangmodteam.workshop.repository.DeviceInfoRepository;
import com.bangmodteam.workshop.repository.JobAppRepository;
import com.bangmodteam.workshop.repository.LocationInfoRepositoty;
import com.bangmodteam.workshop.repository.ProblemCategoryRepository;
import com.bangmodteam.workshop.repository.TicketRepository;
import com.bangmodteam.workshop.repository.UserRepository;
import com.bangmodteam.workshop.utility.SecurityUtility;

@Controller
@RequestMapping("ticket")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepo;
	@Autowired
	private JobAppRepository jobRepo;
	@Autowired
	private ProblemCategoryRepository problemRepo;
	@Autowired
	private LocationInfoRepositoty locationRepo;
	@Autowired
	private DeviceInfoRepository deviceRepo;
	@Autowired
	private UserRepository userRepo;

	@RequestMapping({ "/", "/list" })
	public String index(Model model) {

		User user = userRepo.findByUsername(SecurityUtility.getCurrentUserName());

		model.addAttribute("problemList", problemRepo.findByPositionResponseId(user.getPosition().getId()));
		model.addAttribute("locationList", locationRepo.findByGroupInfoId(user.getGroupInfo().getId()));
		model.addAttribute("deviceList", deviceRepo.findAll());

		List<TicketStatus> ticketStatusList = new ArrayList<>(Arrays.asList(TicketStatus.values()));
		ticketStatusList.removeIf(e -> e.getValue().equalsIgnoreCase("U"));
		model.addAttribute("ticketStatusList", ticketStatusList);

		return "ticket/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TicketDetailDTO>> search(@RequestBody TicketDetailDTO critiria) {

		// TODO: Not Implemented
		return new ResponseEntity<List<TicketDetailDTO>>(new ArrayList<>(), HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(Model model, @RequestParam("jobid") Long jobId) {

		JobApp job = jobRepo.findOne(jobId);
		User leader = userRepo.findByUsername(SecurityUtility.getCurrentUserName());

		TicketDetailDTO ticketDto = new TicketDetailDTO(job);
		Integer sequence = ticketRepo.getMaxTicketSequenceByJobAppId(job.getId()).orElse(0);
		ticketDto.setTicketSequence(sequence + 1);
		model.addAttribute("dataDTO", ticketDto);

		List<UserDetailDTO> userList = new ArrayList<>();

		List<User> userEntities = userRepo.findByUnderLeaderAndLocationsAndPosition(leader.getId(), job.getLocationInfo().getId(), job.getProblemCategory().getPositionResponse().getId());

		if (userEntities != null) {
			userEntities.forEach(e -> userList.add(new UserDetailDTO(e)));
		}

		model.addAttribute("userList", userList);

		List<UserDetailDTO> userOtherList = new ArrayList<>();
		List<User> userOtherEntities = userRepo.findByUnderLeaderAndLocationsAndNotPosition(leader.getId(), job.getLocationInfo().getId(), job.getProblemCategory().getPositionResponse().getId());

		if (userOtherEntities != null) {
			userOtherEntities.forEach(e -> userOtherList.add(new UserDetailDTO(e)));
		}

		model.addAttribute("userOtherList", userOtherList);

		return "ticket/create";

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> createTicket(@RequestBody TicketDetailDTO dataSave) {

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", "");

		if (dataSave == null) {
			responseMsg.replace("message", "Data has no content");
			return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
		}

		try {

			JobApp job = jobRepo.findOne(dataSave.getJobId());
			Ticket newTicket = new Ticket();

			if (job == null) {
				responseMsg.replace("message", "Job hasn't found");
				return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
			}

			if (job.getJobStatus() == JobStatus.OPEN) {

				job.setJobStatus(JobStatus.PROCESS);

			}

			newTicket.setJobApp(job);
			newTicket.setTicketSequence(ticketRepo.getMaxTicketSequenceByJobAppId(dataSave.getJobId()).orElse(0) + 1);
			newTicket.setTicketStatus(TicketStatus.OPEN);
			newTicket.setAssignAt(Calendar.getInstance().getTime());
			newTicket.setAssignRemark(dataSave.getAssignRemark());
			newTicket.setAssignTo(userRepo.findOne(dataSave.getAssignToId()));
			newTicket.setAssignBy(userRepo.findByUsername(SecurityUtility.getCurrentUserName()));

			ticketRepo.save(newTicket);

			responseMsg.replace("message", "success");
			return new ResponseEntity<>(responseMsg, HttpStatus.OK);

		} catch (Exception e) {

			System.out.println(e.getStackTrace());

			responseMsg.replace("message", e.getMessage());
			return new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping("/{ticketId}/view")
	public String view(Model model, @PathVariable("ticketId") Long ticketId) {

		TicketDetailDTO ticketDto = new TicketDetailDTO(ticketRepo.findOne(ticketId));
		model.addAttribute("dataDTO", ticketDto);

		return "ticket/view";
	}

	@RequestMapping(value = "/{ticketId}/take", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> takeTicket(@PathVariable("ticketId") Long ticketId) {

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", "");

		try {

			Ticket ticket = ticketRepo.findOne(ticketId);

			if (ticket == null) {
				responseMsg.replace("message", "Ticket hasn't found");
				return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
			}

			ticket.setTakeAt(Calendar.getInstance().getTime());
			ticket.setTicketStatus(TicketStatus.PROCESS);

			ticketRepo.save(ticket);

			responseMsg.replace("message", "suscess");
			return new ResponseEntity<>(responseMsg, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			responseMsg.replace("message", e.getMessage());
			return new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{ticketId}/finish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> finishTicket(@PathVariable("ticketId") Long ticketId, @RequestBody String resolveDescription) {

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", "");

		try {

			Ticket ticket = ticketRepo.findOne(ticketId);

			if (ticket == null) {
				responseMsg.replace("message", "Ticket hasn't found");
				return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
			}

			ticket.setFinishAt(Calendar.getInstance().getTime());
			ticket.setResovleResult(resolveDescription);
			ticket.setTicketStatus(TicketStatus.FINISH);

			ticketRepo.save(ticket);

			responseMsg.replace("message", "suscess");
			return new ResponseEntity<>(responseMsg, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			responseMsg.replace("message", e.getMessage());
			return new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{ticketId}/close", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> closeTicket(@PathVariable("ticketId") Long ticketId) {

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", "");

		try {

			Ticket ticket = ticketRepo.findOne(ticketId);

			if (ticket == null) {
				responseMsg.replace("message", "Ticket hasn't found");
				return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
			}

			ticket.setVerifyAt(Calendar.getInstance().getTime());
			ticket.setVerifyBy(userRepo.findByUsername(SecurityUtility.getCurrentUserName()));
			ticket.setTicketStatus(TicketStatus.CLOSE);

			ticketRepo.save(ticket);

			responseMsg.replace("message", "suscess");
			return new ResponseEntity<>(responseMsg, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			responseMsg.replace("message", e.getMessage());
			return new ResponseEntity<>(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
