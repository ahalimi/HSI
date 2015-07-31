import java.util.Stack;
/**
 *  This is a modified version of the implementation done by the following authors:
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Anisa Halimi
 */
public class Graph 
{
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    public int a[][];
    public double prev[];
    public int intes[];
    public int s;
    public int g;
    public int f;
    public int o;
    public int c;
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges. param V
     * the number of vertices
     *
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        this.s=0;
        this.g=0;
        this.f=0;
        this.o=0;
        this.c=0;
        a=new int[100][5];
        prev=new double[100];
        intes=new int[100];
        for(int i=0;i<100;i++)
        {
            for(int j=0;j<5;j++)
                a[i][j]=0;
        }
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Initializes a graph from an input stream. The format is the number of
     * vertices <em>V</em>, followed by the number of classes, number of orders,
	 * number of families, number of genera and species. Then for each of the species
	 * there is the name of the class, order, family, genus and species with each 
	 * entry separated by whitespace.
     *
     * @param in the input stream
     * @throws java.lang.IndexOutOfBoundsException if the endpoints of any edge
     * are not in prescribed range
     * @throws java.lang.IllegalArgumentException if the number of vertices or
     * edges is negative
     */
    public Graph(In in) {
        this(in.readInt()); 
        c=in.readInt();
        o=in.readInt();
        f=in.readInt();
        g=in.readInt();
        s=in.readInt();
       int id[][]=new int[s][5];
       String name[][]=new String[s][5];
       int idx=0;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < 5; j++) 
            {
                name[i][j]=in.readString();
                if(i>0)
                {
                    if(name[i][j].equals(name[i-1][j]))
                        id[i][j]=id[i-1][j];
                    else
                    {
                        id[i][j]=idx;
                        idx++;
                    }
                }
                else
                {
                    id[i][j]=idx;
                    idx++;
                }
                a[i][j] = id[i][j];
                System.out.print(a[i][j]+" ");
            }
            prev[i]=in.readDouble();
            intes[i]=in.readInt();
            System.out.print(prev[i]+ " "+intes[i]);
            System.out.println();
        }
        int E = V-1;
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges must be nonnegative");
        }
        
        for (int i = 0; i < s; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                int v=a[i][j];
                int w=a[i][++j];
                if(!hasEdge(v, w))
                {
                    addEdge(v, w);
                }
                j--;
            }
        }
    }

    /**
     * Initializes a new graph that is a deep copy of <tt>G</tt>.
     *
     * @param G the graph to copy
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the graph.
     *
     * @return the number of edges in the graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge v-w to the graph.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0
     * <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) {
            throw new IndexOutOfBoundsException();
        }
        if (w < 0 || w >= V) {
            throw new IndexOutOfBoundsException();
        }
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public boolean hasEdge(int c, int d) {
        boolean stm = false;
        for (int w : adj[c]) {
            if (w == d) {
                stm = true;
                break;
            }
        }
        return stm;
    }
/**
 * Returns the vertices adjacent to vertex <tt>v</tt>.
 *
 * @return the vertices adjacent to vertex <tt>v</tt> as an Iterable
 * @param v the vertex
 * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
 */
public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }


    /**
     * Returns a string representation of the graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}