package code;


public class Solution1041 {

    public static class Vec{
        int x;
        int y;
        Vec direct;
        Vec(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static boolean isRobotBounded(String instructions) {
        char[] chars = instructions.toCharArray();

        Vec origin = new Vec(0, 0 );
        origin.direct = new Vec(0, 1);
        for (int i=0; i<4; i++){
            for (char c : chars){
                int x0 = origin.direct.x;
                int y0 = origin.direct.y;
                switch (c){
                    case 'G':
                        origin.x += origin.direct.x;
                        origin.y += origin.direct.y;
                        break;
                    case 'L':
                        origin.direct.x = y0;
                        origin.direct.y = -x0;
                        break;
                    case 'R':
                        origin.direct.x = -y0;
                        origin.direct.y = x0;
                        break;
                }
            }
        }

        if (origin.x == 0 && origin.y == 0){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isRobotBounded("GL"));
    }

}
