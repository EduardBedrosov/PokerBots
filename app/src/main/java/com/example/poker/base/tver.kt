//package com.example.poker.base
//
//class SortByIf {
//
//    fun sort(num1: Int, num2: Int, num3: Int, num4: Int): List<Int> {
//        var tempMax1: Int = num1
//        var tempMax2: Int = num2
//        var tempMax3: Int = num3
//        var tempMax4: Int = num4
//
//        if ((num1 > num2) && (num1 > num3) && (num1 > num4)) {
//            if ((num2 > num3) && (num2 > num4)) {
//                if (num3 > num4) {
//                    tempMax1 = num1
//                    tempMax2 = num2
//                    tempMax3 = num3
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num1
//                    tempMax2 = num2
//                    tempMax3 = num4
//                    tempMax4 = num3
//                }
//            } else if ((num3 > num2) && (num3 > num4)) {
//                if (num2 > num4) {
//                    tempMax1 = num1
//                    tempMax2 = num3
//                    tempMax3 = num2
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num1
//                    tempMax2 = num3
//                    tempMax3 = num4
//                    tempMax4 = num2
//                }
//            } else if ((num4 > num2) && (num4 > num3)) {
//                if (num2 > num3) {
//                    tempMax1 = num1
//                    tempMax2 = num4
//                    tempMax3 = num2
//                    tempMax4 = num3
//                } else {
//                    tempMax1 = num1
//                    tempMax2 = num4
//                    tempMax3 = num3
//                    tempMax4 = num2
//                }
//            }
//        } else if ((num2 > num1) && (num2 > num3) && (num2 > num4)) {
//            if ((num1 > num3) && (num1 > num4)) {
//                if (num3 > num4) {
//                    tempMax1 = num2
//                    tempMax2 = num1
//                    tempMax3 = num3
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num2
//                    tempMax2 = num1
//                    tempMax3 = num4
//                    tempMax4 = num3
//                }
//            } else if ((num3 > num1) && (num3 > num4)) {
//                if (num1 > num4) {
//                    tempMax1 = num2
//                    tempMax2 = num3
//                    tempMax3 = num1
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num2
//                    tempMax2 = num3
//                    tempMax3 = num4
//                    tempMax4 = num1
//                }
//            } else if ((num4 > num1) && (num4 > num3)) {
//                if (num1 > num3) {
//                    tempMax1 = num2
//                    tempMax2 = num4
//                    tempMax3 = num1
//                    tempMax4 = num3
//                } else {
//                    tempMax1 = num2
//                    tempMax2 = num4
//                    tempMax3 = num3
//                    tempMax4 = num1
//                }
//            }
//        } else if ((num3 > num1) && (num3 > num2) && (num3 > num4)) {
//            if ((num1 > num2) && (num1 > num4)) {
//                if (num2 > num4) {
//                    tempMax1 = num3
//                    tempMax2 = num1
//                    tempMax3 = num2
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num3
//                    tempMax2 = num1
//                    tempMax3 = num4
//                    tempMax4 = num2
//                }
//            } else if ((num2 > num1) && (num2 > num4)) {
//                if (num1 > num4) {
//                    tempMax1 = num3
//                    tempMax2 = num2
//                    tempMax3 = num1
//                    tempMax4 = num4
//                } else {
//                    tempMax1 = num3
//                    tempMax2 = num2
//                    tempMax3 = num4
//                    tempMax4 = num1
//                }
//            } else if ((num4 > num1) && (num4 > num2)) {
//                if (num1 > num2) {
//                    tempMax1 = num3
//                    tempMax2 = num4
//                    tempMax3 = num1
//                    tempMax4 = num2
//                } else {
//                    tempMax1 = num3
//                    tempMax2 = num4
//                    tempMax3 = num2
//                    tempMax4 = num1
//                }
//            }
//        } else if ((num4 > num1) && (num4 > num2) && (num4 > num3)){
//            if ((num1 > num2) && (num1 > num3)) {
//                if (num2 > num3) {
//                    tempMax1 = num4
//                    tempMax2 = num1
//                    tempMax3 = num2
//                    tempMax4 = num3
//                } else {
//                    tempMax1 = num4
//                    tempMax2 = num1
//                    tempMax3 = num3
//                    tempMax4 = num2
//                }
//            } else if ((num2 > num1) && (num2 > num3)) {
//                if (num1 > num3) {
//                    tempMax1 = num4
//                    tempMax2 = num2
//                    tempMax3 = num1
//                    tempMax4 = num3
//                } else {
//                    tempMax1 = num4
//                    tempMax2 = num2
//                    tempMax3 = num3
//                    tempMax4 = num1
//                }
//            } else if ((num3 > num1) && (num3 > num2)) {
//                if (num1 > num2) {
//                    tempMax1 = num4
//                    tempMax2 = num3
//                    tempMax3 = num1
//                    tempMax4 = num2
//                } else {
//                    tempMax1 = num4
//                    tempMax2 = num3
//                    tempMax3 = num2
//                    tempMax4 = num1
//                }
//            }
//        }
//        return listOf(tempMax1, tempMax2, tempMax3, tempMax4)
//    }
//
//}
//
//val sorting = SortByIf()
//
//val num1 = (1..100).random()
//val num2 = (1..100).random()
//val num3 = (1..100).random()
//val num4 = (1..100).random()
//
//val answer: List<Int> = sorting.sort(num1, num2, num3, num4)
