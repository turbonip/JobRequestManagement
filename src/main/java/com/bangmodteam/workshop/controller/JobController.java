package com.bangmodteam.workshop.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.bangmodteam.workshop.dto.UserDetailDTO;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.entity.LocationInfo;
import com.bangmodteam.workshop.entity.ProblemCategory;
import com.bangmodteam.workshop.entity.User;
import com.bangmodteam.workshop.repository.DeviceInfoRepository;
import com.bangmodteam.workshop.repository.JobAppRepository;
import com.bangmodteam.workshop.repository.LocationInfoRepositoty;
import com.bangmodteam.workshop.repository.ProblemCategoryRepository;
import com.bangmodteam.workshop.repository.ProductLineInfoRepository;
import com.bangmodteam.workshop.repository.TicketRepository;
import com.bangmodteam.workshop.repository.UserRepository;
import com.bangmodteam.workshop.utility.DateTimeUtility;
import com.bangmodteam.workshop.utility.JobStatusDbConverter;
import com.bangmodteam.workshop.utility.SecurityUtility;

@Controller()
@RequestMapping("job")
@Secured({ "ROLE_USER" })
public class JobController {

	@Autowired
	private JobAppRepository jobRepository;
	// @Autowired
	// private TicketRepository ticketRepository;
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

		User user = userRepository.findByUsername(SecurityUtility.getCurrentUserName());

		Calendar calenFirstMonth = Calendar.getInstance(Locale.US);
		Calendar calenLastMonth = Calendar.getInstance(Locale.US);

		calenFirstMonth.set(calenFirstMonth.get(Calendar.YEAR), calenFirstMonth.get(Calendar.MONTH), 1);
		calenLastMonth.set(calenLastMonth.get(Calendar.YEAR), calenLastMonth.get(Calendar.MONTH) + 1, 0);
		
		Iterable<LocationInfo> locationList = null;

		if (SecurityUtility.checkAuthorizeByRoleName("ROLE_LEADER")) {

			locationList = locationRepository.findByGroupInfoLeaderId(user.getId());

		} else if (SecurityUtility.checkAuthorizeByRoleName("ROLE_STAFF")) {

			locationList = locationRepository.findByGroupInfoId(user.getGroupInfo().getId());

		} else {

			locationList = locationRepository.findAll();

		}

		model.addAttribute("problemList", problemRepository.findAll());
		model.addAttribute("locationList", locationList);

		List<JobStatus> jobStatusList = new ArrayList<>(Arrays.asList(JobStatus.values()));
		jobStatusList.removeIf(e -> e.getValue().equalsIgnoreCase("U"));
		model.addAttribute("jobStatusList", jobStatusList);

		model.addAttribute("filterStartDate", DateTimeUtility.DateToString(calenFirstMonth.getTime()));
		model.addAttribute("filterEndDate", DateTimeUtility.DateToString(calenLastMonth.getTime()));
		model.addAttribute("filterStartDateDisplay", DateTimeUtility.DateToString(calenFirstMonth.getTime(), "dd/MM/yyyy"));
		model.addAttribute("filterEndDateDisplay", DateTimeUtility.DateToString(calenLastMonth.getTime(), "dd/MM/yyyy"));
		
		return "job/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JobSearchResultDTO>> searchJob(@RequestBody JobSearchDTO search) throws URISyntaxException {

		List<JobSearchResultDTO> results = new ArrayList<JobSearchResultDTO>();

		if (search != null) {

			JobStatus jobStatus = new JobStatusDbConverter().convertToEntityAttribute(search.getJobStatus());
			
			if((jobStatus != JobStatus.UNDIFIEND)){
				
				jobRepository.findBysCriteriaWithStatus(search.getJobId(), search.getProblemCat(), search.getJobLocation() 
													 	//,search.getJobDateBegin(), search.getJobDateEnd()
													 	, jobStatus
							).forEach(entity -> results.add(new JobSearchResultDTO(entity)));
				
			}
			else
			{
				jobRepository.findBysCriteria(search.getJobId(), search.getProblemCat(), search.getJobLocation() 
											 ,search.getJobDateBegin(), search.getJobDateEnd()
							).forEach(entity -> results.add(new JobSearchResultDTO(entity)));
			}

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
		
		JobApp job = jobRepository.findOne(jobId);
		JobDetailDTO dataDto = new JobDetailDTO(job, true);
		model.addAttribute("dataDTO", dataDto);

		//User Assigned//
		List<UserDetailDTO> userOtherList = new ArrayList<>();
		List<UserDetailDTO> userList = new ArrayList<>();
		
		if(SecurityUtility.checkAuthorizeByRoleName("ROLE_LEADER")) {
			
			User leader = userRepository.findByUsername(SecurityUtility.getCurrentUserName());
			
			List<User> userEntities = userRepository.findByUnderLeaderAndLocationsAndPosition(leader.getId(), job.getLocationInfo().getId(), job.getProblemCategory().getPositionResponse().getId());
	
			if (userEntities != null) {
				userEntities.forEach(e -> userList.add(new UserDetailDTO(e)));
			}
	
			List<User> userOtherEntities = userRepository.findByUnderLeaderAndLocationsAndNotPosition(leader.getId(), job.getLocationInfo().getId(), job.getProblemCategory().getPositionResponse().getId());
	
			if (userOtherEntities != null) {
				userOtherEntities.forEach(e -> userOtherList.add(new UserDetailDTO(e)));
			}
			
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("userOtherList", userOtherList);
		//--end User Assigned--//
		
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
