package PLT;

/*
 * This file is a blueprint to create AudioPlayer objects which inherit
 * three fields from Product.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

  /**
   * Instantiates a new Audio player.
   *
   * @param name the name
   * @param manufacturer the manufacturer
   * @param type the type
   * @param audioSpecification the audio specification
   * @param mediaType the media type
   */
  AudioPlayer(
      String name,
      String manufacturer,
      ItemType type,
      String audioSpecification,
      String mediaType) {
    super(name, manufacturer, type);
    setType(ItemType.AUDIO);
    setAudioSpecification(audioSpecification);
    setMediaType(mediaType);
  }

  private void setAudioSpecification(String audioSpecification) {
    this.audioSpecification = audioSpecification;
  }

  /**
   * Gets audio specification.
   *
   * @return the audio specification
   */
  public String getAudioSpecification() {
    return audioSpecification;
  }

  private void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  /**
   * Gets media type.
   *
   * @return the media type
   */
  public String getMediaType() {
    return mediaType;
  }

  public void play() {
    System.out.println("Playing");
  }

  public void stop() {
    System.out.println("Stopping");
  }

  public void next() {
    System.out.println("Next");
  }

  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Shows the class info as a string.
   *
   * @return the class fields as a string
   */
  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: "
        + audioSpecification
        + "\nSupported Playlist Formats: "
        + mediaType;
  }
}
