import java.util.ArrayList;
import java.util.HashMap;

public class ERPriorityQueue{

    public ArrayList<Patient>  patients;
    public HashMap<String,Integer>  nameToIndex;

    public ERPriorityQueue(){

        //  use a dummy node so that indexing starts at 1, not 0

        patients = new ArrayList<Patient>();
        patients.add( new Patient("dummy", 0.0) );

        nameToIndex  = new HashMap<String,Integer>();
    }

    private int parent(int i){
        return i/2;
    }

    private int leftChild(int i){
        return 2*i;
    }

    private int rightChild(int i){
        return 2*i+1;
    }

    public boolean isLeaf(int i){
        if (patients.get(leftChild(i)) == null && patients.get(rightChild(i)) == null){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if (patients.size()==1){
            return true;
        }
        return false;
    }

    public void swap(int i, int j){
        Patient patient1 = patients.get(i);
        Patient patient2 = patients.get(j);
        patients.set(i, patient2);
        patients.set(j, patient1);

        // Swap in hashMap
        nameToIndex.put(patient1.name, j);
        nameToIndex.put(patient2.name, i);
    }

    public void upHeap(int i){
        Patient patient = patients.get(i);

        while (i > 0 && patient.priority < patients.get(parent(i)).priority){ // >1?
            swap(i,parent(i));
            i = parent(i);
        }
    }

    public void downHeap(int i){
        int child;
        while (leftChild(i) < patients.size()){
            child = leftChild(i);
            if (child < patients.size()-1){ // check for smallest child
                if (patients.get(rightChild(i)).priority < patients.get(child).priority){
                    child++;
                }
            }
            if (patients.get(child).priority < patients.get(i).priority){
                swap(i, child);
                i = child;
            }
            else{
                break;
            }
         }
    }

    public boolean contains(String name){
        return nameToIndex.containsKey(name);
    }

    public double getPriority(String name){
        if (contains(name)){
            int i = nameToIndex.get(name);
            return patients.get(i).priority;
        }
        return -1;
    }

    public double getMinPriority(){
        if (patients.size()!=1){
            return patients.get(1).priority;
        }
        return -1;
    }

    public String removeMin(){
        if (patients.size()!=1){
            String name = patients.get(1).name;
            swap(1,patients.size()-1);
            patients.remove(patients.size()-1);
            nameToIndex.remove(name);
            downHeap(1);
            return name;
        }
        return null;
    }

    public String peekMin(){
        if (patients.size()!=1){
            return patients.get(1).name;
        }
        return null;
    }

    /*
     * There are two add methods.  The first assumes a specific priority.
     * The second gives a default priority of Double.POSITIVE_INFINITY
     *
     * If the name is already there, then return false.
     */

    public boolean add(String name, double priority){
        if (contains(name)){
            return false;
        }

        Patient patient = new Patient(name, priority);
        patients.add(patient);
        nameToIndex.put(name,patients.size()-1);
        upHeap(patients.size()-1);
        return true;
    }

    public boolean add(String name){
        if (contains(name)){
            return false;
        }

        Patient patient = new Patient(name, Double.POSITIVE_INFINITY);
        patients.add(patient);
        nameToIndex.put(name,patients.size()-1);
        upHeap(patients.size()-1);
        return true;
    }

    public boolean remove(String name){
        if (!contains(name)){
            return false;
        }
        int i = nameToIndex.get(name);
        swap(patients.size()-1,i);
        patients.remove(patients.size()-1);
        nameToIndex.remove(name);
        downHeap(i);
        return true;

    }

    /*
     *   If new priority is different from the current priority then change the priority
     *   (and possibly modify the heap).
     *   If the name is not there, return false
     */

    public boolean changePriority(String name, double priority){
        if (!contains(name)){
            return false;
        }
        int i = nameToIndex.get(name);
        patients.get(i).setPriority(priority);
        if (patients.get(i).priority < patients.get(parent(i)).priority){
            upHeap(i);
        } else if (patients.get(i).priority > patients.get(parent(i)).priority) {
            downHeap(i);
        }
        return true;
    }

    public ArrayList<Patient> removeUrgentPatients(double threshold){
        ArrayList<Patient> urgentPatients = new ArrayList<>();
        if (patients.size()==1){
            return urgentPatients;
        }

        for (int i = 1; i < patients.size(); i++){
            if (patients.get(i).priority <= threshold){
                urgentPatients.add(patients.get(i));
            }
        }

        for (int i = 0; i < urgentPatients.size(); i++){
            remove(urgentPatients.get(i).name);
        }
        return urgentPatients;
    }

    public ArrayList<Patient> removeNonUrgentPatients(double threshold){
        ArrayList<Patient> nonUrgentPatients = new ArrayList<>();
        if (patients.size()==1){
            return nonUrgentPatients;
        }

        for (int i = 1; i < patients.size(); i++){
            if (patients.get(i).priority >= threshold){
                nonUrgentPatients.add(patients.get(i));
            }
        }
        for (int i = 0; i < nonUrgentPatients.size(); i++){
            remove(nonUrgentPatients.get(i).name);
        }
        return nonUrgentPatients;
    }



    static class Patient{
        private String name;
        private double priority;

        Patient(String name,  double priority){
            this.name = name;
            this.priority = priority;
        }

        Patient(Patient otherPatient){
            this.name = otherPatient.name;
            this.priority = otherPatient.priority;
        }

        double getPriority() {
            return this.priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        String getName() {
            return this.name;
        }

        @Override
        public String toString(){
            return this.name + " - " + this.priority;
        }

        public boolean equals(Object obj){
            if (!(obj instanceof  ERPriorityQueue.Patient)) return false;
            Patient otherPatient = (Patient) obj;
            return this.name.equals(otherPatient.name) && this.priority == otherPatient.priority;
        }
    }
}

