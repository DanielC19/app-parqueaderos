public class lot{
    
    private int id;
    
    private boolean available;
    
    private int user_id;
    
    public boolean avilability(){
        return this.available;
    }
    
    public void reserve(int r){
        user_id = r;
    }
    
    public String getName(){
        return id + ("Está ocupado" + available);
    }
    
}
