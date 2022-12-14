package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.job4j.dreamjob.utils.HttpHelper.setSessionToModel;

@ThreadSafe
@Controller
public class CandidateController {
    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        setSessionToModel(session, model);
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
        setSessionToModel(session, model);
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        int cityId = candidate.getCity().getId();
        City city = cityService.findById(cityId)
                .orElseThrow(() -> new NoSuchElementException("?????????????????? ?????????? ???? ???????????? ???????????? ?????????????????? ??????????????"));
        candidate.setCity(city);
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        int cityId = candidate.getCity().getId();
        City city = cityService.findById(cityId)
                .orElseThrow(() -> new NoSuchElementException("?????????????????? ?????????? ???? ???????????? ???????????? ?????????????????? ??????????????"));
        candidate.setCity(city);
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        Optional<Candidate> candidate = candidateService.findById(id);
        model.addAttribute("candidate",
                candidate.orElseThrow(() -> new NoSuchElementException("???? ???????????? ???????????? ?????? ????????????????????????????")));
        model.addAttribute("cities", cityService.getAllCities());
        setSessionToModel(session, model);
        return "updateCandidate";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId)
                .orElseThrow(() -> new NoSuchElementException(String.format("???? ???????????? ???????????? ???? id %d", candidateId)));
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
