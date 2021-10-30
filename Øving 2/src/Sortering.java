import java.util.Date;
import java.util.Random;

public class Sortering {

    int partition(int arrTilfeldig[], int low, int high)
    {
        int pivot = arrTilfeldig[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (arrTilfeldig[j] < pivot)
            {
                i++;

                // swap arrTilfeldig[i] and arrTilfeldig[j]
                int temp = arrTilfeldig[i];
                arrTilfeldig[i] = arrTilfeldig[j];
                arrTilfeldig[j] = temp;
            }
        }

        // swap arrTilfeldig[i+1] and arrTilfeldig[high] (or pivot)
        int temp = arrTilfeldig[i+1];
        arrTilfeldig[i+1] = arrTilfeldig[high];
        arrTilfeldig[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      arrTilfeldig[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int arrTilfeldig[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arrTilfeldig[pi] is
              now at right place */
            int pi = partition(arrTilfeldig, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arrTilfeldig, low, pi-1);
            sort(arrTilfeldig, pi+1, high);
        }
    }

    public void printArray(int arr[]){
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public void sumSjekkTest(int arr[]){
        int sumFør = 0;
        int sumEtter = 0;

        //Summerer alle verdiene i arrayet før det sorteres
        for (int i = 0; i < arr.length; i++) {
            sumFør += arr[i];
        }

        //Sorterer arrayet
        sort(arr, 0, arr.length-1);

        //Summerer alle verdiene i arrayet igjen
        for (int i = 0; i < arr.length; i++) {
            sumEtter += arr[i];
        }

        //Sammenligner summene og forsikrer at de fortsatt stemmer
        if (sumEtter == sumFør){
            System.out.println("Sumsjekken stemmer");
        }
        else{
            System.out.println("Sumsjekken feilet");
        }
    }

    public void rekkefølgeTest(int arr[]){
        for (int i = 0; i < arr.length-2; i++) {
            if (arr[i+1] < arr[i]){
                System.out.println("Rekkefølgetesten feilet");
                return;
            }
        }
        System.out.println("Rekkefølgetesten var vellykket");
    }

    public static void main(String[] args) {
        Sortering obj = new Sortering();
        DobbelPivotSortering dobbelObj = new DobbelPivotSortering();
        Random rand = new Random();

        int arrayStørrelse = 10000000;

        int arrTilfeldig[] = new int[arrayStørrelse];
        int arrAnnenhver[] = new int[arrayStørrelse];
        int arrSortert[] = new int[arrayStørrelse];

        //Tilfeldig array
        for (int i = 0; i < arrayStørrelse; i++) {
            arrTilfeldig[i] = rand.nextInt(100)+1;
        }

        //Array med annenhver like tal
        for (int i = 0; i < arrayStørrelse; i++) {
            if (i%2 == 0){
                arrAnnenhver[i] = 5;
            }
            else {
                arrAnnenhver[i] = 2;
            }
        }

        //Sortert array
        for (int i = 0; i < arrayStørrelse; i++) {
            arrSortert[i] = 1;
        }

        //Test for å se hvor lang tid sorteringen tar
        Date start = new Date();
        dobbelObj.dualPivotQuickSort(arrSortert, 0, arrayStørrelse-1);
        Date slutt = new Date();
        System.out.println(slutt.getTime()-start.getTime() + "ms\n");

        //Gjennomføring av sumsjekkTest. Testen sjekker summen og sorterer arrayene:
        /*System.out.println("Sumsjekk tester:");
        dobbelObj.sumSjekkTest(arrTilfeldig);
        dobbelObj.sumSjekkTest(arrAnnenhver);
        dobbelObj.sumSjekkTest(arrAnnenhver);*/

        //Gjennomføring av rekkefølgetesten:
        /*System.out.println("\nRekkefølge tester:");
        obj.rekkefølgeTest(arrTilfeldig);
        obj.rekkefølgeTest(arrAnnenhver);
        obj.rekkefølgeTest(arrSortert);*/

    }
}
