package ciic4020.list;

public class TotalCount {
    public static int totalCount(String s, Object[] lists) {
        int count = 0;
        List<String> temp = new ArrayList<String>(lists.length);
        for (int i = 0; i < lists.length; i++) {
            temp = ((List<String>) lists[i]);
            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(j).equals(s)) {
                    count++;
                }
            }
        }
        return count;
    }
}
