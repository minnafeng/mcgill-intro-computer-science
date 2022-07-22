import java.util.LinkedList;

public class Memory {

    public char[] memoryArray;
    public static int idCount;
    public LinkedList<StringInterval> intervalList;

    public Memory(int length){
        //constructor method
        memoryArray = new char[length];
        idCount = 0;
        intervalList = new LinkedList<StringInterval>();
    }

    public String get(int id) {
        String s = "";
        for (int i = 0; i < intervalList.size(); i++) {
            StringInterval strInt = intervalList.get(i);
            if (strInt.id == id) {
                int firstIndex = strInt.start;
                int lastIndex = strInt.start + strInt.length - 1;
                for (int m = firstIndex; m <= lastIndex; m++){
                    s = s.concat(String.valueOf(memoryArray[m]));
                }
                return s;
            }
        }
        return null;
    }

    public int get(String s) {
        int id;
        for (int i = 0; i < intervalList.size(); i++) {
            String x = "";
            StringInterval strInt = intervalList.get(i);
            int firstIndex = strInt.start;
            int lastIndex = strInt.start + strInt.length - 1;
            for (int m = firstIndex; m <= lastIndex; m++) {
                x = x.concat(String.valueOf(memoryArray[m]));
            }
            if (x.equals(s)){
                id = strInt.id;
                return id;
            }

        }
        return -1;
    }

    public int remove(String s) {
        for (int i = 0; i < intervalList.size(); i++) {
            String x = "";
            StringInterval strInt = intervalList.get(i);
            int firstIndex = strInt.start;
            int lastIndex = strInt.start + strInt.length - 1;
            for (int m = firstIndex; m <= lastIndex; m++) {
                x = x.concat(String.valueOf(memoryArray[m]));
            }
            if (x.equals(s)){
                for (int n = firstIndex; n <= lastIndex; n++){
                    memoryArray[n] = '\u0000';
                }
                intervalList.remove(i);
                return strInt.id;
            }
        }
        return -1;
    }

    public String remove(int id) {
        String s = "";
        for (int i = 0; i < intervalList.size(); i++) {
            StringInterval strInt = intervalList.get(i);
            if (strInt.id == id) {
                int firstIndex = strInt.start;
                int lastIndex = strInt.start + strInt.length - 1;
                for (int m = firstIndex; m <= lastIndex; m++){
                    s = s.concat(String.valueOf(memoryArray[m]));
                    memoryArray[m] = '\u0000';
                }
                intervalList.remove(i);
                return s;
            }
        }
        return null;
    }

    public void print(){
        System.out.println(memoryArray);
    }

    public void defragment() {
        for (int i = 0; i < memoryArray.length; i++) {
            if (memoryArray[i] == '\u0000') { // empty space
                int spaceStart = i;
                while (memoryArray[i] == '\u0000' && i<memoryArray.length-1) { // until reach next string
                    i++;
                }
                for (int m = 0; m < intervalList.size(); m++) { // find strInt corresponding to string
                    StringInterval strInt = intervalList.get(m);
                    if (strInt.start == i) { // found strInt
                        strInt.start = spaceStart; // update start of StrInt
                        for (int n = spaceStart; n < spaceStart+strInt.length; n++){ // moving back chars in array
                            memoryArray[n] = memoryArray[i];
                            memoryArray[i] = '\u0000';
                            i++;
                        }
                    } else {
                        continue;
                    }
                    i = spaceStart + strInt.length - 1; // set i to the end of the string moved
                    break;
                }
            } else { // no empty space
                continue;
            }
        }
    }

    public int put(String s) { //implement defragment()
        int space = 0;
        for (int i = 0; i < memoryArray.length; i++){
            if (memoryArray[i] != '\u0000') {
                space = 0;
                continue;
            } else {
                space += 1;
                if (space == s.length()){
                    StringInterval strInt = new StringInterval(idCount, i-space+1, s.length());
                    intervalList.addLast(strInt);
                    int x = strInt.start;
                    for (int m = 0; m < s.length(); m++) {
                        memoryArray[x] = s.charAt(m);
                        x++;
                    }
                    idCount++;
                    return idCount-1;
                }
            }
        }
        defragment();
        for (int n = 0; n < memoryArray.length; n++) {
            if (memoryArray[n] == '\u0000') { // check if space left is big enough
                int counter = 1;
                int i = n;
                while (memoryArray[i] == '\u0000' && i < memoryArray.length-1){
                    counter++;
                    i++;
                }
                if (counter >= s.length()){ //space big enough
                    StringInterval strInt = new StringInterval(idCount, n, s.length());
                    intervalList.addLast(strInt);
                    for (int m = 0; m < s.length(); m++) {
                        memoryArray[n] = s.charAt(m);
                        n++;
                    }
                    idCount++;
                    return idCount - 1;
                }else{
                    return -1;
                }
            }
        }
        return -1;
    }

    public class StringInterval {
        public int id; // unique identifier for the string
        public int start; // index in memory holding the first char of the string
        public int length; // number of char in the string

        StringInterval(int id, int start, int length){
            this.id = id;
            this.start = start;
            this.length = length;
        }
    }
}
