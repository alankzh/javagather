package code;

import java.util.*;


public class Solution954 {
    private static class ANode{
        int value;
        int count;
        ANode(int v, int c){
            this.value = v;
            this.count = c;
        }
        int getValue(){
            return value;
        }

        @Override
        public String toString() {
            return "ANode{" +
                    "value=" + value +
                    ", count=" + count +
                    '}';
        }
    }

    public static boolean canReorderDoubled(int[] A) {
        if (A.length % 2 != 0){
            return false;
        }
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(A.length);
        for (int i=0; i<A.length; i++){
            int a = A[i];
            if (map.containsKey(a)){
                map.put(a, map.get(a)+1);
            } else {
                map.put(a, 1);
            }
        }
        List<ANode> list = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
            list.add(new ANode(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list, Comparator.comparingInt(ANode::getValue));


        for (; list.size()>0; ){
            ANode an = list.get(0);
            if (an.count <= 0){
                list.remove(0);
                continue;
            }

            boolean pair = false;
            for (int i=0; i<list.size(); i++){
                ANode next = list.get(i);
                if (an.getValue() >= 0 && next.value>=0){
                    if (an.value*2 == next.value && next.count>0){
                        an.count -= 1;
                        next.count -= 1;
                        pair = true;
                        break;
                    }
                } else if (an.value <0 && next.value <0){
                    if (next.value*2 == an.value && next.count>0){
                        an.count -= 1;
                        next.count -= 1;
                        pair = true;
                        break;
                    }
                } else {
                    return false;
                }
            }
            if (!pair){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] A = new int[]{-2,-2,1,-2,-1,2};
        boolean b = canReorderDoubled(A);
        System.out.println(b);
    }
}
