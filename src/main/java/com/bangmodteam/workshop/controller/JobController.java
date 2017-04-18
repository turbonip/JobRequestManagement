package com.bangmodteam.workshop.controller;

import java.net.URISyntaxException;
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
import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.dto.JobAppFormDTO;
import com.bangmodteam.workshop.dto.JobDetailDTO;
import com.bangmodteam.workshop.dto.JobSearchDTO;
import com.bangmodteam.workshop.dto.JobSearchResultDTO;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.repository.DeviceInfoRepository;
import com.bangmodteam.workshop.repository.JobAppRepository;
import com.bangmodteam.workshop.repository.LocationInfoRepositoty;
import com.bangmodteam.workshop.repository.ProblemCategoryRepository;
import com.bangmodteam.workshop.repository.ProductLineInfoRepository;
import com.bangmodteam.workshop.repository.TicketRepository;
import com.bangmodteam.workshop.repository.UserRepository;
import com.bangmodteam.workshop.utility.JobStatusDbConverter;
import com.bangmodteam.workshop.utility.SecurityUtility;

@Controller()
@RequestMapping("job")
public class JobController {

	@Autowired
	private JobAppRepository jobRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private ProblemCategoryRepository problemRepository;
	@Autowired
	private LocationInfoRepositoty locationRepository;
	@Autowired
	private DeviceInfoRepository deviceRepository;
	@Autowired
	private ProductLineInfoRepository productLineInfoRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping({ "/", "list" })
	public String index(Model model) {

		model.addAttribute("problemList", problemRepository.findAll());
		model.addAttribute("locationList", locationRepository.findAll());

		List<JobStatus> jobStatusList = new ArrayList<>(Arrays.asList(JobStatus.values()));
		jobStatusList.removeIf(e -> e.getValue().equalsIgnoreCase("U"));
		model.addAttribute("jobStatusList", jobStatusList);

		return "job/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JobSearchResultDTO>> searchJob(@RequestBody JobSearchDTO search) throws URISyntaxException {

		List<JobSearchResultDTO> results = new ArrayList<JobSearchResultDTO>();

		if (search != null) {

			JobStatus jobStatus = new JobStatusDbConverter().convertToEntityAttribute(search.getJobStatus());

			// TODO: Can not search by JobStatus
			jobRepository.findBysCriteria(search.getJobId(), search.getProblemCat(), search.getJobLocation(), jobStatus // ,
																														// search.getJobDateBegin(),
																														// search.getJobDateEnd()
			).forEach(entity -> results.add(new JobSearchResultDTO(entity)));

			if (results.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<JobSearchResultDTO>>(results, HttpStatus.OK);

		} else {

			return new ResponseEntity<List<JobSearchResultDTO>>(results, HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping("/view/{jobId}")
	public String view(@PathVariable Long jobId, Model model) {

		JobDetailDTO dataDto = new JobDetailDTO(jobRepository.findOne(jobId), true);
		model.addAttribute("dataDTO", dataDto);

		return "job/view";
	}

	@RequestMapping("/create")
	public String create(Model model) {

		model.addAttribute("problemList", problemRepository.findAll());
		model.addAttribute("locationList", locationRepository.findAll());
		model.addAttribute("jobStatusList", JobStatus.values());
		model.addAttribute("deviceList", deviceRepository.findAll());

		model.addAttribute("dataDTO", new JobDetailDTO());
		model.addAttribute("formMode", "create");

		return "job/form";
	}

	@RequestMapping(value = "/save/{mode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> save(@PathVariable("mode") String mode, @RequestBody JobAppFormDTO entity) {

		String msg = "";

		if ("create".equalsIgnoreCase(mode)) {

			JobApp newEntity = new JobApp();

			newEntity.setOpenAt(Calendar.getInstance().getTime());
			newEntity.setOpenBy(userRepository.findByUsername(SecurityUtility.getCurrentUserName()));
			newEntity.setJobStatus(JobStatus.OPEN);

			newEntity.setLocationInfo(locationRepository.findOne(entity.getLocationId()));
			newEntity.setProductLineInfo(productLineInfoRepository.findOne(entity.getProductLineId()));

			newEntity.setProblemCategory(problemRepository.findOne(entity.getProblemCatId()));
			newEntity.setDeviceInfo(deviceRepository.findOne(entity.getDeviceInfoId()));

			newEntity.setProblemDescription(entity.getJobDescription());

			jobRepository.save(newEntity);

		}

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", msg);

		return new ResponseEntity<>(responseMsg, HttpStatus.OK);

	}

	@RequestMapping(value = "/closeJob/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> closeJob(@RequestBody JobAppFormDTO entity) {

		Map<String, String> responseMsg = new HashMap<>();
		responseMsg.put("message", "");

		if (entity != null) {

			JobApp job = jobRepository.findOne(entity.getJobId());

			if (job == null) {
				responseMsg.replace("message", "Job hasn't found");
				return new ResponseEntity<>(responseMsg, HttpStatus.NOT_FOUND);
			}

			job.setJobStatus(JobStatus.CLOSE);
			job.setResolveResult(entity.getJobResolve());
			job.setCloseAt(Calendar.getInstance().getTime());
			job.setCloseBy(userRepository.findByUsername(SecurityUtility.getCurrentUserName()));

			jobRepository.save(job);

			responseMsg.replace("message", "success");
			return new ResponseEntity<>(responseMsg, HttpStatus.OK);

		} else {
			responseMsg.replace("message", "RequestBody hasn't content");
			return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
		}

	}

}
