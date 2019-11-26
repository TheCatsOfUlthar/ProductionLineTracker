package plt;

/*
 * This file tells my product subclasses which methods they must implement.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
interface MultimediaControl {

  /** Play. */
  void play();

  /** Stop. */
  void stop();

  /** Previous. */
  void previous();

  /** Next. */
  void next();
}
