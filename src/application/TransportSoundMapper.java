package application;

public class TransportSoundMapper {

    public static String getSoundFileNameForTransport(Transport transportType) {
        switch (transportType) {
            case CYCLE:
                return "/soundEffects/bike.wav";
            case BUS:
                return "/soundEffects/bus.mp3";
            case AIRPLANE:
                return "/soundEffects/plane.mp3";
            case BOAT:
                return "/soundEffects/boat.mp3";
            case TRAIN:
                return "/soundEffects/luas.mp3";
            default:
                return null;
        }
    }
}
