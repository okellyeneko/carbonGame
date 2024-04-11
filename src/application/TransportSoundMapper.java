package application;

public class TransportSoundMapper {

    public static String getSoundFileNameForTransport(Transport transportType) {
        switch (transportType) {
            case CYCLE:
                return "/soundEffects/bike.wav";
            case BUS:
                return "/soundEffects/bus.mp3";
            case LUAS:
                return "/soundEffects/luas.mp3";
            default:
                return null;
        }
    }
}
