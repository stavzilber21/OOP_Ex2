import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {

    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = getGrapgAlgo("src/data/G1.json");
        System.out.println(algo.getGraph());
//        System.out.println("\nAFTER:\n");
//
//        dwg graph = dwgAlgorithm.transpose((dwg) algo);
//        System.out.println(algo.getGraph());
//        algo.getGraph().removeEdge(0,16);
//        algo.getGraph().removeEdge(16,0);
//        algo.getGraph().removeEdge(1,0);
//        algo.getGraph().removeEdge(0,1);
//        boolean f=algo.isConnected();
//        System.out.println(f);
        Node a = (Node)algo.center();
        System.out.println(a);
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new dwgAlgorithm();
        ans.load(json_file);

        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }
}