package com.sii.conference.services;

import com.sii.conference.data.Themed_Path;
import com.sii.conference.data.repositories.ThemedPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemedPathService {

    private final ThemedPathRepository themedPathRepository;

    public void updateOrCreateThemedPath(Themed_Path themedPath) {
        themedPathRepository.save(themedPath);
    }

    public Optional<Themed_Path> findThemedPathById(Integer id) {
        return themedPathRepository.findThemed_PathById(id);
    }

    public List<Themed_Path> findAll() {
        return themedPathRepository.findAllByOrderByIdAsc();
    }

    public void deleteThemedPath(Themed_Path themedPath) {
        themedPathRepository.delete(themedPath);
    }
}
