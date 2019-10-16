package sample;

public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

  private AudioPlayer(
      String name, String manufacturer, String audioSpecification, String mediaType) {
    super(name, manufacturer);
    setAudioSpecification(audioSpecification);
    setMediaType(mediaType);
  }

  private void setAudioSpecification(String audioSpecification) {
    this.audioSpecification = audioSpecification;
  }

  public String getAudioSpecification() {
    return audioSpecification;
  }

  private void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void play() {
    System.out.println("Playing...");
  }

  public void stop() {
    System.out.println("Stopped...");
  }

  public void next() {
    System.out.println("Next...");
  }

  public void previous() {
    System.out.println("Previous...");
  }

  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: "
        + audioSpecification
        + "\nSupported Playlist Formats: "
        + mediaType;
  }

  public static void main(String[] args) {
    AudioPlayer newProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    System.out.println(newProduct);
    newProduct.play();
    newProduct.stop();
    newProduct.next();
    newProduct.previous();
  }
}
