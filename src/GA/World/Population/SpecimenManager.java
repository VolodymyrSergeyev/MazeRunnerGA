package GA.World.Population;

import GA.World.Population.Entity.Specimen;

import java.util.ArrayList;
import java.util.List;

class SpecimenManager {

    private ArrayList<Specimen> topBestRunnerSpecimens;
    private ArrayList<Specimen> topBestGhostSpecimens;
    private ArrayList<Specimen> generalListOfSpecimens;
    private int numberOfBestSpecimens;
    private double lowestfScoreInTheRunnerTop;
    private double lowestfScoreInTheGhostTop;

    SpecimenManager(int populationSize) {
        double precent = populationSize <= 20 ? 1 : populationSize <= 200 ? 0.5 : populationSize <= 500 ? 0.25 : 0.1;
        this.numberOfBestSpecimens = (int) (populationSize * precent);
        this.generalListOfSpecimens = new ArrayList<>();
        this.topBestGhostSpecimens = new ArrayList<>();
        this.topBestRunnerSpecimens = new ArrayList<>();
        this.lowestfScoreInTheRunnerTop = 0;
        this.lowestfScoreInTheGhostTop = 0;
    }

    void calculateTopSpecimens() {
        this.generalListOfSpecimens.forEach(this::addSpecimen);
    }

    void addSpecimen(Specimen specimen) {
        if (!specimen.isTested()) {
            addToGeneralSpecimens(specimen);
        } else {
            if (this.topBestRunnerSpecimens.size() < this.numberOfBestSpecimens && specimen.getRunner() != null) {
                addRunnerBestSpecimen(specimen, 0, true, true);
            } else if (this.topBestGhostSpecimens.size() < this.numberOfBestSpecimens && specimen.getGhost() != null) {
                addRunnerBestSpecimen(specimen, 0, true, false);
            } else {
                this.lowestfScoreInTheRunnerTop = this.topBestRunnerSpecimens.get(this.topBestRunnerSpecimens.size() - 1).getRunnerFScore();
                this.lowestfScoreInTheGhostTop = this.topBestGhostSpecimens.get(this.topBestGhostSpecimens.size() - 1).getGhostFScore();

                if (specimen.getRunnerFScore() > this.lowestfScoreInTheRunnerTop && specimen.getRunner() != null) {
                    findSpotInTheTop(specimen, true);
                }

                if (specimen.getGhostFScore() > this.lowestfScoreInTheGhostTop && specimen.getGhost() != null) {
                    findSpotInTheTop(specimen, false);
                }
            }
        }
    }

    private void findSpotInTheTop(Specimen specimen, boolean runner) {
        boolean added = false;
        int r = 0;
        if (runner) {
            ArrayList<Specimen> s1 = this.topBestRunnerSpecimens;

            for (int i = 0; i < s1.size(); i++) {
                if (s1.get(i).getRunner().getGenome().equals(specimen.getRunner().getGenome())) {
                    break;
                }
                if (s1.get(i).getRunnerFScore() < specimen.getRunnerFScore() && !added) {
                    r = s1.indexOf(s1.get(i));
                    added = true;
                }
                if (i == s1.size() - 1 && added) {
                    addRunnerBestSpecimen(specimen, r, false, true);
                }
            }
        } else {
            ArrayList<Specimen> s2 = this.topBestGhostSpecimens;
            for (int i = 0; i < s2.size(); i++) {
                if (s2.get(i).getGhost().getGenome().equals(specimen.getGhost().getGenome())) {
                    break;
                }
                if (s2.get(i).getGhostFScore() < specimen.getGhostFScore() && !added) {
                    r = s2.indexOf(s2.get(i));
                    added = true;
                }
                if (i == s2.size() - 1 && added) {
                    addRunnerBestSpecimen(specimen, r, false, false);
                }
            }
        }
    }

    private void addToGeneralSpecimens(Specimen specimen) {
        this.generalListOfSpecimens.add(specimen);
    }

    private void addRunnerBestSpecimen(Specimen specimen, int rank, boolean first, boolean runner) {
        List<Specimen> tmp;
        boolean added = false;
        if (runner) {
            if (rank > 0 || !first) {
                tmp = this.topBestRunnerSpecimens.subList(rank, this.topBestRunnerSpecimens.size());
                this.topBestRunnerSpecimens = new ArrayList<>(this.topBestRunnerSpecimens.subList(0, rank));
                Specimen tmpS = new Specimen(specimen.getRunner(), null, specimen.getRunnerFScore(), Double.NaN);
                tmpS.setTested();
                this.topBestRunnerSpecimens.add(tmpS);
                if (this.topBestRunnerSpecimens.size() + tmp.size() > this.numberOfBestSpecimens) {
                    tmp.remove(tmp.size() - 1);
                }
                this.topBestRunnerSpecimens.addAll(tmp);
            } else {
                ArrayList<Specimen> s = this.topBestRunnerSpecimens;
                int r = 0;
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i).getRunner().getGenome().equals(specimen.getRunner().getGenome())) {
                        break;
                    }
                    if (s.get(i).getRunnerFScore() < specimen.getRunnerFScore() && !added) {
                        r = s.indexOf(s.get(i));
                        added = true;
                    }
                    if (i == s.size() - 1 && added) {
                        addRunnerBestSpecimen(specimen, r, false, true);
                    }
                }
                if (!added) {
                    Specimen tmpS = new Specimen(specimen.getRunner(), null, specimen.getRunnerFScore(), Double.NaN);
                    tmpS.setTested();
                    this.topBestRunnerSpecimens.add(tmpS);
                }
            }
        } else {
            if (rank > 0 || !first) {
                tmp = this.topBestGhostSpecimens.subList(rank, this.topBestGhostSpecimens.size());
                this.topBestGhostSpecimens = new ArrayList<>(this.topBestGhostSpecimens.subList(0, rank));
                Specimen tmpS = new Specimen(null, specimen.getGhost(), Double.NaN, specimen.getGhostFScore());
                tmpS.setTested();
                this.topBestGhostSpecimens.add(tmpS);
                if (this.topBestGhostSpecimens.size() + tmp.size() > this.numberOfBestSpecimens) {
                    tmp.remove(tmp.size() - 1);
                }
                this.topBestGhostSpecimens.addAll(tmp);
            } else {
                ArrayList<Specimen> s = this.topBestGhostSpecimens;
                int r = 0;
                for (int i = 0; i < s.size(); i++) {
                    if (s.get(i).getGhost().getGenome().equals(specimen.getGhost().getGenome())) {
                        break;
                    }
                    if (s.get(i).getGhostFScore() < specimen.getGhostFScore() && !added) {
                        r = s.indexOf(s.get(i));
                        added = true;
                    }
                    if (i == s.size() - 1 && added) {
                        addRunnerBestSpecimen(specimen, r, false, false);
                    }
                }
                if (!added) {
                    Specimen tmpS = new Specimen(null, specimen.getGhost(), Double.NaN, specimen.getGhostFScore());
                    tmpS.setTested();
                    this.topBestGhostSpecimens.add(tmpS);
                }
            }
        }
    }

    ArrayList<Specimen> getTopBestRunnerSpecimens() {
        return topBestRunnerSpecimens;
    }

    ArrayList<Specimen> getGeneralListOfSpecimens() {
        return generalListOfSpecimens;
    }

    ArrayList<Specimen> getTopBestGhostSpecimens() {
        return topBestGhostSpecimens;
    }

    void removeWeakSpecimens() {
        this.generalListOfSpecimens = new ArrayList<>();
        this.generalListOfSpecimens.addAll(this.topBestRunnerSpecimens);
        this.generalListOfSpecimens.addAll(this.topBestGhostSpecimens);
    }

    int getNumberOfBestSpecimens() {
        return numberOfBestSpecimens;
    }

    void clearTops() {
        this.topBestGhostSpecimens = new ArrayList<>();
        this.topBestRunnerSpecimens = new ArrayList<>();
    }
}
