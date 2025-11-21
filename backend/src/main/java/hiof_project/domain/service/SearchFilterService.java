package hiof_project.domain.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class SearchFilterService {
    public <T> ArrayList<T> search(ArrayList<T> list,
                                   java.util.function.Function<T, String> textExtractor,
                                   String query) {

        ArrayList<T> searchResult = new ArrayList<>();

        if (query == null || query.isBlank()) {
            return list; // vis alt
        }

        String q = query.toLowerCase();

        for (T item : list) {
            String text = textExtractor.apply(item);
            if (text == null) continue;

            if (text.toLowerCase().contains(q)) {
                searchResult.add(item);
            }
        }

        return searchResult;
    }
}

