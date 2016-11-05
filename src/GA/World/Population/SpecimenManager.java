package GA.World.Population;

import GA.World.Population.Entity.Specimen;

import java.util.ArrayList;
import java.util.List;

public class SpecimenManager {

    private ArrayList<Specimen> topBestRunnerSpecimens;
    private ArrayList<Specimen> topBestGhostSpecimens;
    private ArrayList<Specimen> generalListOfSpecimens;
    private int numberOfBestSpecimens;

    public SpecimenManager(int populationSize) {
        this.numberOfBestSpecimens = (int) (populationSize * 0.1);
        this.generalListOfSpecimens = new ArrayList<>();
        this.topBestGhostSpecimens = new ArrayList<>();
        this.topBestRunnerSpecimens = new ArrayList<>();
    }

    public void calculateTopSpecimens() {
        for(int i = 0; i < this.generalListOfSpecimens.size(); i++){
            addSpecimen(this.generalListOfSpecimens.get(i));
        }
    }

    public void addSpecimen(Specimen specimen) {
        if(!specimen.isTested()){
            addToGeneralSpecimens(specimen);
        }else {
            if (getCorrectSpecimenBestList(specimen).size() < this.numberOfBestSpecimens) {
                addBestSpecimen(specimen, 0, true);
            }
            findSpotInTheTop(specimen);
        }
    }

    private void findSpotInTheTop(Specimen specimen) {
        boolean added = false;
        ArrayList<Specimen> s = getCorrectSpecimenBestList(specimen);
        int r = 0;
        for (int i = 0; i < s.size(); i++) {
            if (getSpecimenGenome(s.get(i)).equals(getSpecimenGenome(specimen))) {
                break;
            }
            if (s.get(i).getFScore() < specimen.getFScore() && !added) {
                r = s.indexOf(s.get(i));
                added = true;
            }
            if(i == s.size() - 1 && added){
                addBestSpecimen(specimen, r, false);
            }
        }
    }

    private ArrayList<Integer> getSpecimenGenome(Specimen s) {
        if (s.isRunner()){
            return s.getRunner().getGenome();
        }
        return s.getGhost().getGenome();
    }

    private void addToGeneralSpecimens(Specimen specimen) {
        this.generalListOfSpecimens.add(specimen);
    }

    private void addBestSpecimen(Specimen specimen, int rank, boolean first) {
        List<Specimen> tmp;
        boolean added = false;
        if (rank > 0 || !first) {
            tmp = getCorrectSpecimenBestList(specimen).subList(rank, getCorrectSpecimenBestList(specimen).size());
            setCorrectSpecimenBestList(specimen, new ArrayList<>(getCorrectSpecimenBestList(specimen).subList(0, rank)));
            getCorrectSpecimenBestList(specimen).add(specimen);
            if(getCorrectSpecimenBestList(specimen).size() + tmp.size() > this.numberOfBestSpecimens){
                tmp.remove(tmp.size()-1);
            }
            getCorrectSpecimenBestList(specimen).addAll(tmp);
        } else {
            ArrayList<Specimen> s = getCorrectSpecimenBestList(specimen);
            int r = 0;
            for (int i = 0; i < s.size(); i++) {
                if (getSpecimenGenome(s.get(i)).equals(getSpecimenGenome(specimen))) {
                    break;
                }
                if (s.get(i).getFScore() < specimen.getFScore() && !added) {
                    r = s.indexOf(s.get(i));
                    added = true;
                }
                if(i == s.size() - 1 && added){
                    addBestSpecimen(specimen, r, false);
                }
            }
            if (!added) {
                getCorrectSpecimenBestList(specimen).add(specimen);
            }
        }
    }

    private void setCorrectSpecimenBestList(Specimen s, ArrayList<Specimen> l) {
        if (s.isRunner()) {
            this.topBestRunnerSpecimens = l;
        } else {
            this.topBestGhostSpecimens = l;
        }
    }

    private ArrayList<Specimen> getCorrectSpecimenBestList(Specimen specimen) {
        if (specimen.isRunner()) {
            return this.topBestRunnerSpecimens;
        }
        return this.topBestGhostSpecimens;
    }

    public ArrayList<Specimen> getTopBestRunnerSpecimens() {
        return topBestRunnerSpecimens;
    }

    public void setTopBestRunnerSpecimens(ArrayList<Specimen> topBestRunnerSpecimens) {
        this.topBestRunnerSpecimens = topBestRunnerSpecimens;
    }

    public ArrayList<Specimen> getGeneralListOfSpecimens() {
        return generalListOfSpecimens;
    }

    public void setGeneralListOfSpecimens(ArrayList<Specimen> generalListOfSpecimens) {
        this.generalListOfSpecimens = generalListOfSpecimens;
    }

    public ArrayList<Specimen> getTopBestGhostSpecimens() {
        return topBestGhostSpecimens;
    }

    public void setTopBestGhostSpecimens(ArrayList<Specimen> topBestGhostSpecimens) {
        this.topBestGhostSpecimens = topBestGhostSpecimens;
    }

    public void removeWeakSpecimens() {
        this.generalListOfSpecimens = new ArrayList<>();
    }
}
