
package frc.robot;

public class util {
  //198 instructions
  public static int[] fromHSV_slow(short h, short s, short v) {
    final int region = (h / 30) % 6;
    final int chroma = (s * v) / 255;
    final int m = v - chroma;
    final int X = (chroma * ((h % 30) * 255 / 30)) >> 8;

    switch (region) {
        case 0:
            return new int[]{v, X + m, m};
        case 1:
            return new int[]{v - X, v, m};
        case 2:
            return new int[]{m, v, X + m};
        case 3:
            return new int[]{m, v - X, v};
        case 4:
            return new int[]{X + m, m, v};
        default:
            return new int[]{v, m, v - X};
    }
}
  // made more perf better from this
  // https://github.com/wpilibsuite/allwpilib/blob/main/wpilibj/src/main/java/edu/wpi/first/wpilibj/util/Color.java
  //https://godbolt.org/#z:OYLghAFBqd5TKALEBjA9gEwKYFFMCWALugE4A0BIEAZgQDbYB2AhgLbYgDkAjF%2BTXRMiAZVQtGIHgBYBQogFUAztgAKAD24AGfgCsp5eiyahUAUgBMAIUtXyKxqiIEh1ZpgDC6egFc2TA3cAGQImbAA5PwAjbFIQC3IAB3QlYhcmL19/A2TU5yEQsMi2GLiEh2wndJEiFlIiTL8AnntsR3ymGrqiQojo2Pj7WvrG7JalYZ7QvpKBiwBKe3QfUlROLksAZlDUXxwAajNNjwA3SpJSI9wzLQBBG9uT9AJMfcx0AH0ldA4PgHcyABrCChIjzI42O4PJ4vfaJUjoNZKJQfYjYNhKCAYJgTfYTTAgEBnJxkI4eUFXSwANn2xPmhwA7JDbvtWfsWD4SHiAF6HTYAEVplQAdKludgIODNsy2ftBKQIPjCWLsB8iPsCHzBVoIRq%2BR4ebqCLZbPSzEyHrLZe8vj9VQDSMDiWYAKxWY0u/lSmVs838h5%2Bh5cRb0bgu/gBLg6cjobgeE3WPHLVbYQ4WTZ8chEbTBxaAkAurSGbjSfhsAtFyPR2NcfhKEBF7NR4PkOCwFAYHD4YhkSjUOiMVgcbiZwTCMQSTgyOTCZRqTTN8j6BJGExoBN2CpVVwQdyjAIWFrBabFUogBlJFJpIT7%2BItXLXpi9U8DC9bjpdEbeJp31rtaqTM%2B/RxG%2Bky3oeQzdEBswgYs3wrGs3BbDseypmSxIXJSUJ3DCrw2t8vwOsCoLegGOHPK88KItgyKokQ6KYtiuJKkS5ykscFKbNcFg0nSjI%2Bqy8oQByXJomw%2BwgEKqBmhadxWmy%2BF2v8QIgvRbCkXJvoMv6ULaUGIZhhGOYxnGG5JghaHphY/BNjo8yLEg2AsDgcSSsWXCluQ5aFuQVb8DWdYNlmOb2eQ%2BbSC6wpUgAnDw0VaFSWhaC6FiHtFmzuZsRmLgFwXNosbbIGgPyJAwsR9liJVlXEuzGMAPCbFoLQDvRpD1hAUTGVEoR1AAniO/AYGwHDCAA8kw9D9YuOBsHVkjTQQpDnAQZz1ou2DqJUnLrJmoJtMZ9AEFEpB9V4ODGUQpAEOWvAtjQRjAEoABqBDYH8o2JMwA0zqI4iSNOY6KCoGjGfoLSrqYG6GEd9aQIs6CJB0a0ALSjVlf7LTue7fmM5DHkUwE5FeHS3vexPpNBZ7jG0mOdGBOPNBj2501BJ6E%2BM9NZIzEyswTMFSHByaIS0l3YDtLahlw4a%2BcZNbqAAHFSyNUtI%2By1SY%2BwNcKWjCjw%2BwQPG1i2OQ%2BzdhcaYZibXhsKVjCkBbPDzDZIWth2VV2xVQ22wM6v1Y1zUMK17WdYu3WsKQU2ZkNI1EONk3GTNc3rNGhBLVUq3GRtW30d9e2S9Gh3HadWDJzZV03Xwiz3Swj0vW9H1fbdP0Tv9siA3OIOLsuhh1euRvWNDUSw25CNI9wqPo%2B%2B6RuEwngM4Es%2BUwMZN5OkpOXqvBRs/z1P/kIn4NPPu%2B0wfS9xBz3Trzz9RnwLSwWYMoviwZUvZdW3AK0rKtqxDmubNrut9aGxsAPU2hBzZbASPsa23t7aQKdnlOyrsQCEBoDQCqgMW5TjbvIDuC4C5IHrODQhqCaBEF6p9IKpBCGDGoUoUh5DKFaBftLPyJkuD8gIGg/Ydc/ixH2J/ZWqtfZ/wAXrE4SgBGKyET/OqoidaZlsrmcgjlnIDDcvmF00hhSNXllodK0VorSAZAyPRDIqTuU8uWHgSUZY5W4IFBISjQqS2snY9%2BtZEHKLOG1ae0ggA
  // 189 instructions faster
  public static int[] fromHSV(short h, short s, short v) {
      //final int chroma = (s * v) / 255;        
      final int m = v - (s * v) / 255; 
      final int X = (
                  (s * v) / 255  * 
                  (int)Math.round((h % 30) * (255 / 30.0))
      ) >> 8;

      switch ( (int)(h / 30) % 6 )   {
        case 0:
          return new int[]{v, X + m, m};
        case 1:
          return new int[]{v - X, v, m};
        case 2:
          return new int[]{m, v, X + m};
        case 3:
          return new int[]{m, v - X, v};
        case 4:
          return new int[]{X + m, m, v};
        default:
          return new int[]{v, m, v - X};
      }
  }

}
