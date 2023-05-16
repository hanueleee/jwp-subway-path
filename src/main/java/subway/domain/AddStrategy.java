package subway.domain;

import java.util.List;
import java.util.Optional;

public interface AddStrategy {
    void activate(List<Section> sections, Station upStation, Station downStation, int distance);

    default Optional<Section> findSectionByStationExistsAtDirection(
            final List<Section> sections,
            final Station station,
            final Direction direction
    ) {
        return sections.stream()
                .filter(section -> section.isStationExistsAtDirection(station, direction))
                .findFirst();
    }
}
