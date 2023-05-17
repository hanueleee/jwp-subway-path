package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LineTest {

    public static final Station STATION_A = new Station(1L, "A");
    public static final Station STATION_B = new Station(2L, "B");
    public static final Station STATION_C = new Station(3L, "C");
    public static final Station STATION_D = new Station(4L, "D");

    @DisplayName("라인 최초 생성")
    @Test
    void createLine() {
        // given
        // when
        Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

        // then
        assertThat(line.getName()).isEqualTo("2호선");
        assertThat(line.getSections()).hasSize(1);
    }

    @Nested
    @DisplayName("라인에 section 추가")
    class addSection {

        @Nested
        @DisplayName("성공")
        class AddSuccess {
            @Test
            void case1() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 8);

                // when
                line.addSection(STATION_D, STATION_B, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_D, STATION_B, STATION_C);
            }

            @Test
            void case2() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 8);

                // when
                line.addSection(STATION_B, STATION_D, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_B, STATION_D, STATION_C);
            }

            @Test
            void case3() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                line.addSection(STATION_C, STATION_A, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_C, STATION_A, STATION_B);
            }

            @Test
            void case4() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                line.addSection(STATION_A, STATION_C, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_C, STATION_B);
            }

            @Test
            void case5() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                line.addSection(STATION_C, STATION_B, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_C, STATION_B);
            }

            @Test
            void case6() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                line.addSection(STATION_B, STATION_C, 3);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_B, STATION_C);
            }
        }

        @Nested
        @DisplayName("실패")
        class AddFail {
            @Test
            @DisplayName("해당 노선에 두 역이 모두 이미 존재하는 경우")
            void case1() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                // then
                assertThatThrownBy(() -> line.addSection(STATION_A, STATION_B, 3))
                        .hasMessage("해당 노선에 두 역이 모두 존재합니다.");
            }

            @Test
            @DisplayName("해당 노선에 두 역이 모두 존재하지 않는 경우")
            void case2() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                // then
                assertThatThrownBy(() -> line.addSection(STATION_C, STATION_D, 3))
                        .hasMessage("해당 노선에 두 역이 모두 존재하지 않습니다.");
            }

            @Test
            @DisplayName("추가하려는 section의 거리가 기존 section사이의 거리보다 긴 경우")
            void case3() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);

                // when
                // then
                assertThatThrownBy(() -> line.addSection(STATION_A, STATION_C, 12))
                        .hasMessage("추가하려는 거리가 기존의 거리보다 깁니다.");
            }
        }
    }

    @Nested
    @DisplayName("라인에서 역 제거")
    class DeleteStationFromLine {

        @Nested
        @DisplayName("성공")
        class deleteSuccess {

            @Test
            void case1() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 3);

                // when
                line.deleteStation(STATION_A);

                // then
                assertThat(line.getStations()).containsExactly(STATION_B, STATION_C);
            }

            @Test
            void case2() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 3);

                // when
                line.deleteStation(STATION_B);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_C);
            }

            @Test
            void case3() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 3);

                // when
                line.deleteStation(STATION_C);

                // then
                assertThat(line.getStations()).containsExactly(STATION_A, STATION_B);
            }
        }

        @Nested
        @DisplayName("실패")
        class AddFail {
            @Test
            @DisplayName("해당 역히 해당 노선에 존재하지 않는 경우")
            void case1() {
                // given
                Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
                line.addSection(STATION_B, STATION_C, 3);

                // when
                // then
                assertThatThrownBy(() -> line.deleteStation(STATION_D))
                        .hasMessage("해당 역이 해당 노선에 존재하지 않습니다.");
            }
        }
    }

    @Test
    @DisplayName("해당 노선에 존재하는 모든 역 반환")
    void getStations() {
        // when
        Line line = Line.createLine("2호선", STATION_A, STATION_B, 10);
        line.addSection(STATION_B, STATION_C, 3);
        line.addSection(STATION_A, STATION_D, 5);

        assertThat(line.getStations()).containsExactly(STATION_A, STATION_D, STATION_B, STATION_C);
    }
}
