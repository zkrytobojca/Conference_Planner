package com.sii.conference.services;

import com.sii.conference.data.ThemedPath;
import com.sii.conference.data.repositories.ThemedPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemedPathService {

    private final ThemedPathRepository themedPathRepository;

    public void updateOrCreateThemedPath(ThemedPath themedPath) {
        themedPathRepository.save(themedPath);
    }

    public Optional<ThemedPath> findThemedPathById(Integer id) {
        return themedPathRepository.findThemedPathById(id);
    }

    public List<ThemedPath> findAll() {
        return themedPathRepository.findAllByOrderByIdAsc();
    }

    public void deleteThemedPath(ThemedPath themedPath) {
        themedPathRepository.delete(themedPath);
    }
}
