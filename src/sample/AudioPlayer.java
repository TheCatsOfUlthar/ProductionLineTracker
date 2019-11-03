/*
 * This file is a blueprint to create AudioPlayer objects which inherit
 * three fields from Product.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */

package sample;

public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

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

  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: "
        + audioSpecification
        + "\nSupported Playlist Formats: "
        + mediaType;
  }
}
