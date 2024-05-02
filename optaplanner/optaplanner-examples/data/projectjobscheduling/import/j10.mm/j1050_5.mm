************************************************************************
file with basedata            : mm50_.bas
initial value random generator: 1256871276
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  12
horizon                       :  76
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     10      0        9        4        9
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          1           8
   3        3          2           6  10
   4        3          2           5   7
   5        3          2           9  10
   6        3          1          11
   7        3          1           9
   8        3          3           9  10  11
   9        3          1          12
  10        3          1          12
  11        3          1          12
  12        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     4       9    5    0    8
         2     8       7    4    7    0
         3    10       7    4    0    7
  3      1     4       8    7    0    5
         2     7       5    5    2    0
         3     9       3    3    0    2
  4      1     2       6   10    0    7
         2     8       5    8    0    5
         3     9       2    7    0    5
  5      1     4       9    9    5    0
         2     4       7    6    0   10
         3     5       5    2    4    0
  6      1     1       5    8    5    0
         2     4       4    8    4    0
         3     4       3    6    0    7
  7      1     2       7    8    4    0
         2     5       5    4    0    9
         3     6       5    3    0    9
  8      1     1       6    4    4    0
         2     8       6    4    0    7
         3    10       4    3    0    7
  9      1     1       9    9    5    0
         2     7       8    8    5    0
         3     7       9    7    0    3
 10      1     3       4    9    0    5
         2     4       3    5    0    4
         3    10       3    1    8    0
 11      1     4       5   10    6    0
         2     5       4    9    5    0
         3     6       4    9    0    6
 12      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   26   23   23   36
************************************************************************
