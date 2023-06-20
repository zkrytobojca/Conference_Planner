package com.sii.conference.controllers;

import com.sii.conference.data.ThemedPath;
import com.sii.conference.services.ThemedPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/themedPath")
public class ThemedPathController {

    private final ThemedPathService themedPathService;

    @PostMapping
    public ResponseEntity<String> addNewThemedPath(@RequestBody ThemedPath newThemedPath)
    {
        ThemedPath createdThemedPath = themedPathService.createThemedPath(newThemedPath);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdThemedPath.getId())
                .toUri();

        return ResponseEntity.created(location).body("Themed Path created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemedPath> getThemedPath(@PathVariable("id") Integer id)
    {
        final Optional<ThemedPath> foundThemedPath = themedPathService.findThemedPathById(id);
        return ResponseEntity.of(foundThemedPath);
    }

    @GetMapping
    public ResponseEntity<List<ThemedPath>> getAllThemedPaths() {
        List<ThemedPath> themedPathList = themedPathService.findAll();
        return ResponseEntity.ok().body(themedPathList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThemedPath> updateThemedPath(@PathVariable("id") Integer id, @RequestBody ThemedPath newThemedPath)
    {
        Optional<ThemedPath> updatedThemedPath = themedPathService.updateThemedPath(id, newThemedPath);
        return ResponseEntity.of(updatedThemedPath);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ThemedPath> deleteThemePath(@PathVariable("id") Integer id)
    {
        final Optional<ThemedPath> themePathToDelete = themedPathService.findThemedPathById(id);

        if (themePathToDelete.isPresent()) {
            themedPathService.deleteThemedPath(themePathToDelete.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
