// dummy pokemon class

public class Pokemon implements Comparable<Pokemon> {
	String name;
	int lvl;
	String type;
	
	public Pokemon (String n, int lvl, String t) {
		this.name = n;
		this.lvl = lvl;
		this.type = t;
	}
	
	public String toString() {
		return this.name + ": lvl " + this.lvl + " " + this.type + " Pokemon";
	}

	@Override
	public int compareTo(Pokemon P) {
		if (this.lvl < P.lvl) {
			return -1;
		} else if (this.lvl > P.lvl) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
