package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public String index(Model model){
        model.addAttribute("title","MyJobs");
        Iterable<Job> jobs;
        jobs = jobRepository.findAll();
        model.addAttribute("jobs",jobs);
        return "index";
    }

    @GetMapping("/add")
    public String displayAddJobForm(@NotNull Model model) {
	model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        List<Employer> employers =(List<Employer>) employerRepository.findAll();
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("employers",employers);
        model.addAttribute("skills",skills);
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam (required = false) List<Integer> skills) {

        if (errors.hasErrors()) {
	    model.addAttribute("title", "Add Job");
            return "add";
        } else {
            Optional<Employer> result =employerRepository.findById((employerId));
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Employer");
            } else {
                Employer selectedEmployer = result.get();
                newJob.setEmployer(selectedEmployer);
            }
            List<Skill> skillJobs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillJobs);
        }
        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> result = jobRepository.findById(jobId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Job");
        } else {
            Job selectedJob = result.get();
            model.addAttribute("job", selectedJob);
        }
            return "view";
    }

}
