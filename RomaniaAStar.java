import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

public class RomaniaAStar{

    public static void main(String[] args){     //------------------------------------MAIN--------------------------------
       	
       	RomaniaProblem prblm = new RomaniaProblem("Lugoj", "Bucharest");
       	SearchAgent_UCS agent = new SearchAgent_UCS();
       	Node node = agent.solve(prblm);
        node.printBackTrace();
    }
}

class SearchAgent_UCS{           //------------------------------Intelligent Agent-----------------------------------------
	
	public Node solve(RomaniaProblem prblm){

	    	//define and initialize START NODE, FRINGE and EXPLORED SET (for GRAPH SEARCH)
			State start_state = prblm.getStartState();
			Node selected_node = null;
			int num_nodes_pushed_to_fringe = 0;
			//The A* search logic is implemented the customized compare function of the PriorityQueue class
			PriorityQueue<Node> fringe = new PriorityQueue<>(1, new Comparator<Node>() {
				public int compare(Node i, Node j){
					return (int)((i.pathCost + prblm.heuristic(i.state)) - (j.pathCost+ prblm.heuristic(j.state)));
				}
			});
			ArrayList<State> explored_set = new ArrayList<>();
			fringe.add(new Node(start_state));


		do {
			//select a node from fringe for expansion based on UCS (use PriorityQueue)
			//insert the node into explored list
			System.out.println("Fringe	:	" + fringe);
			selected_node = fringe.poll();
			System.out.println("Selected Node	:	" + selected_node);
			if(prblm.goalTest(selected_node.state)){
				System.out.println("Number of nodes pushed into the fringe:	        " + num_nodes_pushed_to_fringe);
				System.out.println("Number of nodes expanded before reaching the goal:	" + explored_set.size());
				return selected_node;
			} 
			if(!explored_set.contains(selected_node.state)) explored_set.add(selected_node.state);
			System.out.println("Expanded Nodes	:	" + explored_set);
			Action[] successors = prblm.succFunc(selected_node.state);
			for(int i=0; i < successors.length; i++){
				Action successor = successors[i];
				Node successor_node = new Node(successor.successor);
				successor_node.pathCost = selected_node.pathCost + successor.cost;
				successor_node.parent = selected_node;
				System.out.println("Successor " + i + ": " + " name ->" + successor.successor.city + ",  Cost -> " + successor_node.pathCost + ",  heuristic value-> "+ prblm.heuristic(successor.successor));
				if (!explored_set.contains(successor.successor) && !fringe.contains(successor_node)){
					fringe.add(successor_node); 
					num_nodes_pushed_to_fringe++;
				} 
			}
			System.out.println("------------------------------------");

			// do goal test,
			// if true, return current node
			// else insert children obtained from successor function into fringe

		} while (!fringe.isEmpty());

   //if not find the goal return an empty node
		return new Node(new State(""));	   
	}

}

class State {                        //-------------------States in problem formulation-------------------------------------
	public String city;

	public State(String city){
		this.city = city;
	}
	public String toString(){
        return this.city;
    }
	
	@Override
	public boolean equals(Object o){
		boolean retVal = false;
		if (o instanceof State){
            State ptr = (State) o;
            retVal = ptr.city == this.city;
        }
		return retVal;
	}
}

class Action {                      //--------------------Actions in problem formulation------------------------------------
	public State successor;
	public double cost;

	public Action(State succ, double c){
		this.successor = succ;
		this.cost = c;
	}
	public String toString(){
        return (successor.toString()+", "+cost);
    }
}

class Node{                         //-----------------------Nodes in the search tree----------------------------------------
    public final State state;
    public double pathCost;
    public Node parent;     //will be actually point to a Node object

    public Node(State stValue){
        this.state = stValue;
        this.pathCost=0;
        this.parent = null; 
    }
    public String toString(){
        return this.state.city;
    }
    public void printBackTrace() {
        if (parent != null)
           parent.printBackTrace();
        System.out.println("\t" + this.state.city + "\t" + pathCost);
     }

	@Override
	public boolean equals(Object o){
		boolean retVal = false;
		if (o instanceof Node){
            Node ptr = (Node) o;
            retVal = ptr.state == this.state;
        }
		return retVal;
	}
}

class RomaniaProblem {               //----------------------------------problem formulation----------------------------------
	State startState;
	State goalState;
	//state space defined by initial state, actions, transition model

	public RomaniaProblem(String start, String goal){
		startState = new State(start);
        goalState = new State(goal);
	}
	public State getStartState(){
		return startState;
	}
	public boolean goalTest(State st){
		if ( st.city.equals(this.goalState.city) ) return true;
		else
			return false;
	}
	public Action[] succFunc(State st){
		Action[] children = new Action[]{};
		
		if (st.city.equals("Arad"))
			children = new Action[]{
				new Action(new State("Zerind"),75),
				new Action(new State("Sibiu"),140),
				new Action(new State("Timisoara"),118) 
			};
		else if (st.city=="Zerind")
			children = new Action[]{
	            new Action(new State("Arad"),75),
	            new Action(new State("Oradea"),71)
	        };
		else if (st.city=="Oradea")
			children= new Action[]{
	            new Action(new State("Zerind"),71),
	            new Action(new State("Sibiu"),151)
	        };
		else if (st.city=="Sibiu")
			children = new Action[]{
	            new Action(new State("Arad"),140),
	            new Action(new State("Fagaras"),99),
	            new Action(new State("Oradea"),151),
	            new Action(new State("Rimnicu Vilcea"),80),
	        };
		else if (st.city=="Fagaras")
			children = new Action[]{
	            new Action(new State("Sibiu"),99),
	            new Action(new State("Bucharest"),211)
	        };
		else if (st.city=="Rimnicu Vilcea")
			children = new Action[]{
	            new Action(new State("Sibiu"),80),
	            new Action(new State("Pitesti"),97),
	            new Action(new State("Craiova"),146)
	        };
		else if (st.city=="Pitesti")
			children = new Action[]{
	            new Action(new State("Rimnicu Vilcea"),97),
	            new Action(new State("Bucharest"),101),
	            new Action(new State("Craiova"),138)
	        };
		else if (st.city=="Timisoara")
			children = new Action[]{
	            new Action(new State("Arad"),118),
	            new Action(new State("Lugoj"),111)
	        };
		else if (st.city=="Lugoj")
			children = new Action[]{
	            new Action(new State("Timisoara"),111),
	            new Action(new State("Mehadia"),70)
	        };
		else if (st.city=="Mehadia")
			children = new Action[]{
	            new Action(new State("Lugoj"),70),
	            new Action(new State("Drobeta"),75)
	        };
		else if (st.city=="Drobeta")
			children = new Action[]{
	            new Action(new State("Mehadia"),75),
	            new Action(new State("Craiova"),120)
	        };
		else if (st.city=="Craiova")
			children = new Action[]{
	            new Action(new State("Drobeta"),120),
	            new Action(new State("Rimnicu Vilcea"),146),
	            new Action(new State("Pitesti"),138)
	        };
		else if (st.city=="Bucharest")
			children = new Action[]{
	            new Action(new State("Pitesti"),101),
	            new Action(new State("Giurgiu"),90),
	            new Action(new State("Fagaras"),211)
	        };
		else if (st.city=="Giurgiu")
			children = new Action[]{
	            new Action(new State("Bucharest"),90)
	        };

		return children;

	}

	//The straight-line heuristic from Bucharest to other cities
	public double heuristic(State st) {
		double heuristic = 0;
		if (st.city.equals("Arad")) heuristic = 366;
			
		else if (st.city=="Zerind") heuristic = 374;

		else if (st.city=="Oradea")	heuristic = 380;
		
		else if (st.city=="Sibiu") heuristic = 253;
			
		else if (st.city=="Fagaras") heuristic = 176;
		
		else if (st.city=="Rimnicu Vilcea") heuristic = 193;
		
		else if (st.city=="Pitesti") heuristic = 100;
		
		else if (st.city=="Timisoara") heuristic = 329;
		
		else if (st.city=="Lugoj") heuristic = 244;
		
		else if (st.city=="Mehadia") heuristic = 241;
		
		else if (st.city=="Drobeta") heuristic = 242;
		
		else if (st.city=="Craiova") heuristic = 160;
		
		else if (st.city=="Bucharest") heuristic = 0;
		
		else if (st.city=="Giurgiu") heuristic = 77;

		else if (st.city=="Eforie") heuristic = 161;

		else if (st.city=="Iasi") heuristic = 226;

		else if (st.city=="Hirsova") heuristic = 151;

		else if (st.city=="Neamt") heuristic = 234;

		else if (st.city=="Vaslui") heuristic = 199;

		else if (st.city=="Urziceni") heuristic = 80;
			
		return heuristic;
	}

}