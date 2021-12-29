package org.example.deadCold.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Graph {
    private List<List<MatrixItem>> matrix;
    private List<List<Double>> multiDistanceDesire = new ArrayList<>();
    private final ArrayList<Node> nodes;
    private double firstShortestWay;
    private double secondShortestWay;
    private int[] shortestWay;// = new int[]{1104, 1108, 1109, 1111, 365, 197, 204, 1101, 1107, 1105, 536, 554, 563, 571, 117, 98, 104, 105, 107, 120, 193, 119, 103, 106, 207, 100, 101, 605, 602, 607, 599, 603, 616, 611, 604, 614, 112, 111, 759, 109, 102, 118, 99, 115, 116, 110, 108, 113, 114, 574, 561, 768, 763, 765, 769, 762, 760, 764, 767, 761, 968, 971, 969, 974, 979, 972, 966, 975, 981, 976, 973, 967, 978, 982, 964, 288, 278, 556, 564, 573, 558, 567, 529, 551, 525, 543, 531, 513, 523, 539, 540, 521, 552, 576, 553, 577, 549, 572, 542, 566, 578, 524, 559, 509, 520, 555, 507, 537, 515, 550, 506, 511, 544, 517, 533, 545, 560, 508, 547, 532, 575, 569, 527, 557, 541, 519, 570, 535, 516, 528, 526, 538, 548, 268, 271, 270, 510, 546, 562, 512, 514, 530, 565, 579, 518, 568, 945, 522, 946, 954, 949, 955, 953, 952, 942, 883, 872, 871, 286, 275, 279, 272, 287, 282, 283, 289, 277, 281, 280, 284, 269, 273, 274, 977, 980, 285, 276, 965, 667, 671, 672, 970, 669, 487, 488, 168, 159, 162, 161, 155, 158, 160, 156, 167, 165, 164, 65, 67, 72, 71, 75, 66, 74, 449, 451, 69, 68, 73, 70, 453, 446, 447, 448, 450, 85, 444, 668, 445, 452, 670, 673, 80, 76, 86, 77, 91, 84, 89, 87, 79, 83, 81, 88, 90, 82, 78, 874, 879, 878, 877, 875, 881, 884, 876, 882, 880, 873, 870, 725, 733, 727, 722, 956, 941, 950, 937, 948, 951, 935, 633, 726, 728, 735, 254, 247, 255, 264, 245, 258, 243, 251, 252, 244, 259, 261, 260, 256, 248, 246, 249, 257, 262, 263, 253, 250, 734, 729, 730, 732, 468, 627, 628, 634, 469, 464, 456, 471, 481, 467, 474, 303, 482, 454, 473, 140, 630, 152, 939, 154, 1106, 1102, 1103, 145, 143, 150, 146, 153, 147, 141, 144, 306, 27, 33, 31, 34, 35, 25, 32, 37, 26, 151, 367, 368, 366, 363, 360, 358, 362, 200, 201, 359, 205, 195, 198, 206, 203, 192, 199, 194, 196, 208, 202, 623, 608, 606, 598, 600, 615, 610, 620, 1076, 498, 1074, 1072, 1071, 496, 1070, 495, 922, 923, 924, 915, 934, 925, 917, 933, 919, 1069, 1073, 1075, 1068, 499, 621, 609, 613, 618, 597, 612, 617, 619, 505, 502, 501, 500, 503, 504, 680, 1001, 999, 678, 684, 675, 676, 681, 677, 679, 683, 766, 770, 908, 909, 906, 910, 907, 492, 486, 489, 490, 485, 491, 169, 904, 911, 905, 674, 682, 796, 783, 786, 166, 157, 163, 138, 133, 131, 139, 136, 125, 137, 749, 757, 740, 127, 267, 179, 898, 886, 890, 885, 902, 892, 265, 752, 754, 744, 755, 746, 739, 748, 745, 742, 741, 743, 747, 751, 758, 750, 737, 753, 738, 736, 756, 378, 387, 391, 379, 394, 392, 377, 381, 374, 1, 372, 395, 376, 0, 380, 388, 382, 370, 375, 386, 371, 390, 426, 433, 432, 430, 431, 423, 420, 434, 419, 863, 862, 422, 429, 428, 425, 421, 427, 424, 393, 389, 384, 383, 373, 385, 899, 891, 896, 903, 897, 296, 295, 293, 294, 241, 893, 888, 894, 901, 889, 895, 887, 900, 235, 242, 237, 238, 240, 236, 239, 869, 211, 209, 212, 210, 866, 865, 868, 864, 867, 1066, 1067, 1064, 1063, 1065, 178, 175, 170, 177, 174, 173, 171, 172, 176, 266, 41, 38, 42, 43, 39, 40, 130, 121, 129, 122, 123, 132, 126, 135, 128, 124, 789, 784, 794, 799, 787, 785, 792, 800, 797, 534, 790, 791, 134, 793, 788, 795, 798, 779, 775, 781, 774, 778, 772, 776, 777, 656, 655, 46, 50, 62, 49, 48, 51, 64, 708, 839, 696, 699, 700, 702, 694, 698, 701, 687, 994, 341, 332, 330, 343, 334, 333, 342, 336, 339, 335, 695, 707, 691, 697, 709, 688, 689, 690, 692, 685, 686, 704, 703, 706, 693, 835, 841, 847, 838, 824, 840, 845, 823, 846, 825, 848, 855, 837, 834, 826, 854, 830, 1032, 1028, 1024, 1017, 348, 1094, 1099, 1096, 1093, 1100, 1097, 406, 401, 414, 1091, 1081, 1089, 1078, 1079, 1077, 494, 493, 291, 292, 290, 810, 805, 804, 802, 807, 813, 814, 803, 811, 806, 809, 812, 1010, 801, 808, 1009, 1008, 1005, 1011, 1007, 714, 718, 717, 721, 712, 711, 713, 720, 710, 719, 716, 715, 1006, 180, 181, 19, 17, 16, 15, 20, 23, 24, 18, 21, 22, 1085, 1080, 1090, 1088, 1092, 1082, 1086, 1084, 1083, 1087, 217, 96, 223, 232, 220, 218, 219, 231, 409, 411, 402, 403, 415, 410, 400, 396, 417, 405, 404, 399, 408, 407, 216, 228, 213, 224, 229, 225, 221, 233, 226, 230, 214, 234, 222, 97, 187, 189, 190, 191, 182, 186, 188, 185, 184, 183, 95, 93, 92, 215, 227, 94, 984, 985, 987, 986, 983, 1012, 1014, 412, 1013, 1016, 1015, 416, 418, 413, 397, 398, 318, 958, 961, 963, 960, 959, 635, 641, 640, 648, 644, 643, 636, 638, 647, 4, 11, 8, 646, 637, 329, 326, 310, 312, 315, 328, 317, 323, 311, 313, 325, 316, 324, 321, 322, 314, 320, 319, 327, 2, 6, 5, 9, 7, 12, 3, 10, 13, 14, 639, 642, 645, 650, 652, 649, 651, 654, 653, 962, 1023, 1021, 1027, 1098, 1095, 1018, 1025, 1019, 1029, 1026, 1022, 1020, 1031, 1030, 990, 859, 991, 992, 988, 989, 440, 439, 437, 438, 443, 442, 441, 435, 436, 832, 820, 857, 833, 860, 831, 861, 815, 817, 853, 818, 829, 816, 858, 851, 827, 852, 850, 856, 822, 819, 828, 849, 843, 836, 821, 844, 842, 1050, 1036, 1055, 1041, 1051, 1046, 1039, 1034, 1053, 1045, 1038, 1048, 1059, 1060, 1043, 1044, 1037, 1061, 1052, 1057, 1040, 666, 658, 663, 661, 659, 660, 45, 59, 1047, 1035, 63, 47, 54, 1042, 1056, 1062, 1058, 1054, 1049, 1033, 60, 52, 58, 55, 53, 662, 664, 665, 657, 773, 771, 780, 1003, 1002, 1004, 1000, 932, 926, 918, 916, 57, 61, 913, 914, 921, 931, 927, 920, 928, 930, 929, 44, 56, 996, 998, 705, 993, 995, 912, 997, 345, 331, 338, 346, 340, 344, 497, 347, 624, 622, 601, 369, 364, 361, 149, 142, 337, 142, 148, 29, 36, 28, 355, 352, 350, 357, 354, 349, 353, 356, 351, 596, 30, 591, 594, 593, 581, 595, 582, 583, 589, 587, 590, 588, 585, 580, 592, 584, 586, 300, 298, 297, 307, 302, 299, 304, 309, 305, 308, 301, 476, 462, 477, 458, 459, 475, 480, 478, 782, 457, 484, 465, 472, 470, 483, 466, 460, 455, 463, 461, 479, 723, 731, 724, 631, 632, 626, 629, 625, 938, 940, 957, 936, 947, 944, 943, 1110, 1104};
    private final int nodeToDuple;

    public Graph(ArrayList<Node> nodes, Expression distanceFounder, int nodeToDuple) {
        this.nodes = nodes;
        this.nodeToDuple = nodeToDuple;
        shortestWay = new int[nodes.size()+2];
        for (int i = 0;i<shortestWay.length;i++){
            shortestWay[i] = i;
        }
        generateMatrix(distanceFounder);
        cookDistanceDesire();
    }

    private void generateMatrix(Expression distanceFounder) {
        double pheromone = RouteConstants.PHEROMONE;
        this.matrix = new ArrayList<>();
        for (Node value : this.nodes) {
            ArrayList<MatrixItem> localList = new ArrayList<>();
            for (Node node : this.nodes) {
                localList.add(new MatrixItem(distanceFounder.getDistance(new double[]{value.getLongitude(),
                        value.getLatitude(), node.getLongitude(), node.getLatitude()}), pheromone));
            }
            this.matrix.add(localList);
        }
        this.matrix.add(matrix.get(nodeToDuple));
        for (int i = 0; i < nodes.size(); i++) {
            this.matrix.get(i).add(this.matrix.get(nodeToDuple).get(i));
        }
        this.matrix.get(this.nodes.size()).add(new MatrixItem(0.0, pheromone));

        this.nodes.add(new Node(this.nodes.get(nodeToDuple).toString().split(" "), this.nodes.size()));
    }

    private void cookDistanceDesire() {
        final double POW_DISTANCE = RouteConstants.POW_DISTANCE;
        final int DISTANCE_FACTOR = RouteConstants.DISTANCE_FACTOR;
        for (int i = 0; i < matrix.size(); i++) {
            List<Double> localList = new ArrayList<>();
            for (int j = 0; j < matrix.size(); j++) {
                localList.add(Math.pow(DISTANCE_FACTOR / matrix.get(i).get(j).distance, POW_DISTANCE));
            }
            this.multiDistanceDesire.add(localList);
        }
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                matrix.append(this.matrix.get(i).get(j).pheromone).append(' ');
            }
            matrix.append("\n");
        }
        return "matrix = " + matrix + "FirstShortestWay = " + firstShortestWay + ' ' + "SecondShortestWay = " + secondShortestWay + "ShortestWay = " + Arrays.toString(shortestWay);
    }


    public int length() {
        return nodes.size();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public List<List<MatrixItem>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<MatrixItem>> matrix) {
        this.matrix = matrix;
    }

    public void setFirstShortestWay(double firstShortestWay) {
        this.firstShortestWay = firstShortestWay;
    }

    public void setSecondShortestWay(double secondShortestWay) {
        this.secondShortestWay = secondShortestWay;
    }

    public double getFirstShortestWay() {
        return firstShortestWay;
    }

    public double getSecondShortestWay() {
        return secondShortestWay;
    }

    public List<List<Double>> getMultiDistanceDesire() {
        return multiDistanceDesire;
    }

    public void setMultiDistanceDesire(List<List<Double>> multiDistanceDesire) {
        this.multiDistanceDesire = multiDistanceDesire;
    }

    public void setShortestWay(int[] shortestWay) {
        this.shortestWay = shortestWay;
    }

    public int[] getShortestWay() {
        return shortestWay;
    }

    public int getNodeToDuple() {
        return nodeToDuple;
    }
}
