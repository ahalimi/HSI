/**
 *  Calculates  hstilda, HSI, heco, HSE as described in the paper.
 *  @author Anisa Halimi
**/
public class HSI 
{
    public static Graph G;
    public HSI(Graph G)
    {
        this.G=G;
    }
    
    public int distance(int s, int d) {
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);
        int cnt = 0, dis;
        if (dfs.hasPathTo(d)) {
            for (int x : dfs.pathTo(d)) 
            {
                if (x == s) 
                   ; 
                else 
                    cnt++;
            }
        }
        dis=cnt-1;
        return dis;
    }
    
    public int H(int x)
    {
        if(x>0)
            return 1;
        else
            return 0;
    }
    
    public double hstilda()
    {
        int sum=0;
        for(int l1=0;l1<G.s-1;l1++)
        {
            for(int l2=l1+1;l2<G.s;l2++)
            {
                if(l1!=l2)
                    sum+=distance(G.a[l1][4], G.a[l2][4]);
            }
        }
        if(G.s==1)
            return 0;
        else
            return 2.0*sum/(7*G.s*(G.s-1));
    }
    
    public double HS()
    {
        double hs;
        hs=(1-1/100.0+(G.s+hstilda())/100)*H(2-G.g)+
           (2-1/25.0+G.g/50.0+(G.s+hstilda())/5000)*H(G.g-1)*H(2-G.f)+
           (4-8/50.0+4*G.f/50.0+G.g/(50*100/4.0)+(G.s+hstilda())/(50*100*100/4))*H(G.f-1)*H(2-G.o)+
           (8-32/100.0+8*G.o/32.0+G.f/(4*32*50.0)+G.g/(4*32*50*100.0)+(G.s+hstilda())/(100*100*4*32*50))*H(2-G.c)*H(G.o-1)+
           (16-32/5.0+16*G.c/5.0+G.o/(5*32/16.0)+G.f/(5*50*32/16.0)+G.g/(5*50*32*100/16.0)+(G.s+hstilda())/(5*50*32*100*100/16))*H(G.c-1);
        return hs;
    }
    
    public double heco()
    {
        double pom=0, heco;
        for(int i=0;i<G.s;i++)
        {
            pom+=G.prev[i]*(1-Math.exp(0.9-G.intes[i]));
        }
        heco=pom/G.s;
        return heco;
    }
    
    public double HSE()
    {
        double hse;
        hse=(1-1/100.0+(G.s+hstilda()*(1-heco())-H(2-G.s)*heco())/100)*H(2-G.g)+
            (2-1/25.0+G.g/50.0+(G.s+hstilda()*(1-heco()))/5000)*H(G.g-1)*H(2-G.f)+
            (4-8/50.0+4*G.f/50.0+G.g/(50*100/4.0)+(G.s+hstilda()*(1-heco()))/(50*100*100/4))*H(G.f-1)*H(2-G.o)+
            (8-32/100.0+8*G.o/32.0+G.f/(4*32*50.0)+G.g/(4*32*50*100.0)+(G.s+hstilda()*(1-heco()))/(100*100*4*32*50))*H(2-G.c)*H(G.o-1)+
            (16-32/5.0+16*G.c/5.0+G.o/(5*32/16.0)+G.f/(5*50*32/16.0)+G.g/(5*50*32*100/16.0)+(G.s+hstilda()*(1-heco()))/(5*50*32*100*100/16))*H(G.c-1);
        return hse;
    }
}