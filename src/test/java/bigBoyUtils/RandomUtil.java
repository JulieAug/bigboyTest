package bigBoyUtils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author anthony
 * @date Jan 25, 2018
 * @updateTime 5:56:42 PM
 */
public class RandomUtil {
  public static final Set<String> DIGITS = new ConcurrentSkipListSet<String>();

  /**
   * 获取最短UUID
   * 
   * @return
   */
  public static long getLeastUID() {
    long uid = UUID.randomUUID().getLeastSignificantBits();
    return uid;
  }

  /**
   * 随机生成几个数
   * 
   * @return
   */
  public synchronized static String getDigits(int i) {
    String s = RandomStringUtils.randomNumeric(i);
    while (DIGITS.contains(s)) {
      s = RandomStringUtils.randomNumeric(i);
    }
    DIGITS.add(s);
    return s;
  }
}
