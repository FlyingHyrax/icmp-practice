import java.util.*;

public class WelcomeHard_ian {

  public static void main( String[] _ ) {
    main( new Scanner(System.in) );
    }

  public static void main( Scanner in ) {
    int n = in.nextInt(); in.nextLine();
    for (int i=0;  i<n;  ++i) {
      System.out.printf( "Case #%d: %04d%n", i+1,
//                         new WelcomeHard_ian( in.nextLine(), "welcome to code jam", (int)Math.pow(10,4) ).solve(0,0) );
      					 new WelcomeHard_ian( in.nextLine(), "welcome to code jam", (int)Math.pow(10,4) ).solveDP() );
      }
    }


  // I'd like to make a static method, which contains a recursive function within its scope.
  // But this is java, so I'll work around that.
  //
  String src;
  String target;
  int M;
  Integer[][] memo;
  WelcomeHard_ian( String _src, String _target ) { this(_src,_target,Integer.MAX_VALUE); }
  WelcomeHard_ian( String _src, String _target, int _M ) {
    this.src = _src;
    this.target = _target;
    this.M = _M;
    this.memo = new Integer[this.src.length()+1][this.target.length()+1];
    }
  
  /* Return the number of occurences of target.substring(t)
   * contained as sub-sequences inside src.substring(s).
   */
  int solve( int s, int t ) {
    if (memo[s][t] == null) {
      if (t==target.length()) {
        memo[s][t] = 1;
        }
      else if (s == src.length()) {
        memo[s][t] = 0;
        }
      else {
        memo[s][t] = ( solve(s+1,t)
                       + (src.charAt(s)==target.charAt(t)
                          ? solve(s+1,t+1)
                          : 0 ))
                     % M;
        }
      }
    //dbg( "s=%d,t=%d: memo: %s", s,t,Arrays.deepToString(memo) );
    return memo[s][t];
    }

    
  /* Return the number of occurences of target.substring(t)
   * contained as sub-sequences inside src.substring(s).
   */
  int solveDP() {
    int[] memoColS1 = new int[ target.length()+1 ];
    
    // this being 0s is the base-case: s==src.length
    int[] memoColS  = new int[ target.length()+1 ];
    memoColS[target.length()] = 1;
    
    // Fill the table right-to-left, bottom-to-top.
    // entry(s,t) is the #subsequences of target.substring(t)
    //  occuring in src.substring(s).
    // We only maintain two columns at a time.
    
    for (int s = src.length()-1;  s>=0;  --s ) {
      int[] tmpSwap = memoColS1;
      memoColS1 = memoColS;           // account for --s
      memoColS  = tmpSwap;
      
      // Now fill column s, bottom-to-top:
      memoColS[target.length()] = 1;  // Base case, t==target.length
      for (int t = target.length()-1;  t>=0;  --t) {
        memoColS[t] = (  memoColS1[t]
                       + (src.charAt(s)==target.charAt(t)
                          ? memoColS1[t+1]
                          : 0 ))
                     % M;
        }
        dbg( "col %d: %s\ncol %d: %s\n",
             s+1,Arrays.toString(memoColS1),
             s,Arrays.toString(memoColS) );
      }
    return memoColS[0];
    }
    
    
  static void test() {
    System.out.printf( "Expect 1,2,3,3,1,1,0:%n" );
    System.out.printf( "%d%n", new WelcomeHard_ian("a","a").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("aa","a").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("aaa","a").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("aaa","aa").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("aa","aa").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("ab","ab").solve(0,0) );
    System.out.printf( "%d%n", new WelcomeHard_ian("ab","ba").solve(0,0) );
    
    System.out.printf( "Using DP:%n" );
    System.out.printf( "%d%n", new WelcomeHard_ian("a","a").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("aa","a").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("aaa","a").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("aaa","aa").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("aa","aa").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("ab","ab").solveDP() );
    System.out.printf( "%d%n", new WelcomeHard_ian("ab","ba").solveDP() );
    }
    
  static void dbg( String fmt, Object... args ) {
    System.err.printf( fmt+"%n", args );
    }
  }