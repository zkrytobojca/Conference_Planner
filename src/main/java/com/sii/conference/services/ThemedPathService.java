package com.sii.conference.services;

import com.sii.conference.data.ThemedPath;
import com.sii.conference.data.User;
import com.sii.conference.data.repositories.ThemedPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemedPathService {

    private final ThemedPathRepository themedPathRepository;

    public ThemedPath createThemedPath(ThemedPath themedPath) {
        return themedPathRepository.save(themedPath);
    }

    public Optional<ThemedPath> updateThemedPath(Integer id, ThemedPath themedPath) {
        final Optional<ThemedPath> themedPathOptional = findThemedPathById(id);

        ThemedPath oldThemedPath = null;
        if (themedPathOptional.isPresent()) {
            oldThemedPath = themedPathOptional.get();
            if (themedPath.getTheme() != null) oldThemedPath.setTheme(themedPath.getTheme());
            return Optional.of(themedPathRepository.save(oldThemedPath));
        }
        else return Optional.empty();
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
