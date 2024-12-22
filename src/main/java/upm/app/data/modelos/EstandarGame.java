package upm.app.data.modelos;

public class EstandarGame extends Game {

    private static final int MINIMUM_ADVANTAGE = 20;

    public EstandarGame() {
        super();
    }

    @Override
    public void serviceWon() {
        if (gameWon()) {
            throw new InvalidAttributeException("Ya se ha ganado el juego");
        }
        if (this.service == GamePoints.FORTY.getValue() && this.rest != GamePoints.ADVANTAGE.getValue()) {
            this.service = GamePoints.ADVANTAGE.getValue();
        } else if (this.rest == GamePoints.ADVANTAGE.getValue()) {
            this.rest = GamePoints.FORTY.getValue();
        } else {
            this.service = GamePoints.next(this.service);
        }
    }

    @Override
    public void restWon() {
        if (gameWon()) {
            throw new InvalidAttributeException("Ya se ha ganado el juego");
        }
        if (this.rest == GamePoints.FORTY.getValue() && this.service != GamePoints.ADVANTAGE.getValue()) {
            this.rest = GamePoints.ADVANTAGE.getValue();
        } else if (service == GamePoints.ADVANTAGE.getValue()) {
            this.service = GamePoints.FORTY.getValue();
        } else {
            this.rest = GamePoints.next(this.rest);
        }
    }

    @Override
    public boolean gameWon() {
        if (this.service == GamePoints.WIN_AD.getValue() || this.rest == GamePoints.WIN_AD.getValue()) {
            return true;
        }
        return ((this.service >= GamePoints.ADVANTAGE.getValue() && this.service - this.rest >= MINIMUM_ADVANTAGE) ||
                (this.rest >= GamePoints.ADVANTAGE.getValue() && this.rest - this.service >= MINIMUM_ADVANTAGE));
    }

    public enum GamePoints {
        FIFTEEN(15),
        THIRTY(30),
        FORTY(40),
        ADVANTAGE(50),
        WIN_AD(60);

        private final int value;

        GamePoints(int value) {
            this.value = value;
        }

        public static int next(int current) {
            return switch (current) {
                case 0 -> FIFTEEN.getValue();
                case 15 -> THIRTY.getValue();
                case 30 -> FORTY.getValue();
                case 40 -> ADVANTAGE.getValue();
                case 50 -> WIN_AD.getValue();
                default -> throw new IllegalStateException("Expresion invalida: " + current);
            };
        }

        public int getValue() {
            return value;
        }
    }
}