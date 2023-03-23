package io.learnk8s.knote.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.learnk8s.knote.repository.Note;
import io.learnk8s.knote.repository.NotesRepository;

@Controller
class KNoteController {

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        getAllNotes(model);
        return "index";
    }

    @PostMapping(value = "/note")
    public String saveNotes(@RequestParam("image") MultipartFile file,
                            @RequestParam String description,
                            @RequestParam(required = false) String publish,
                            @RequestParam(required = false) String upload,
                            Model model) throws IOException {

        if (publish != null && publish.equals("Publish")) {
            saveNote(description, model);
            getAllNotes(model);
            return "redirect:/";
        }
        // After save fetch all notes again
        return "index";
    }

    private void getAllNotes(Model model) {
        List<Note> notes = notesRepository.findAll();
        Collections.reverse(notes);
        model.addAttribute("notes", notes);
    }

    private void saveNote(String description, Model model) {
        if (description != null && !description.trim().isEmpty()) {
          notesRepository.save(new Note(null, description.trim()));
          //After publish you need to clean up the textarea
          model.addAttribute("description", "");
        }
    }
}