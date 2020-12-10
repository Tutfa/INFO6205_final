package FinalProject.src.variable;

import java.util.Random;

public class func {
    private static final Random r = new Random();

    /*
    * stdX = (X - U) / sigma
    * X = sigma * stdX + u;
    *
    * */
    public static double stdGaussian(double sigma, double u){
        double X = r.nextGaussian();

        return sigma * X + u;
    }

}
