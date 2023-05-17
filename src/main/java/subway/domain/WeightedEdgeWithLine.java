package subway.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class WeightedEdgeWithLine extends DefaultWeightedEdge {
    private Line line;

    public WeightedEdgeWithLine(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return line;
    }
}
