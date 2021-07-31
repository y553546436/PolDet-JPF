/*
 * Copyright (C) 2021 Pu Yi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You can find a copy of the GNU General Public License at
 * <http://www.gnu.org/licenses/>.
 */


package gov.nasa.jpf.test.mc.data;

import gov.nasa.jpf.annotation.BitFlip;
import gov.nasa.jpf.util.test.TestJPF;
import gov.nasa.jpf.vm.Verify;

import org.junit.Test;
import org.junit.Ignore;

/**
 * regression tests for the getBitFlip API and the BitFlipListener
 */
public class BitFlipTest extends TestJPF {

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  public void checkBitFlip(int data) {
    int seen = Verify.getCounter(0);
    assert ((seen & data) == 0);
    seen |= data;
    Verify.setCounter(0, seen);
  }

  public static int staticMethod1(@BitFlip int bar) {
    return bar;
  }

  @Test
  public void testAnnotatedStaticMethodParameterBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("@BitFlip annotation for static method parameters test");
      int d = staticMethod1(0);
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  public static int staticMethod2(int bar) {
    return bar;
  }

  @Test
  public void testCommandLineSpecifiedStaticMethodParameterBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.params=foo",
                "+bitflip.foo.method=gov.nasa.jpf.test.mc.data.BitFlipTest.staticMethod2(int)",
                "+bitflip.foo.name=bar")){
      System.out.println("command line specified bit flip for static method parameters test");
      int d = staticMethod2(0);
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  public int instanceMethod1(@BitFlip int bar) {
    return bar;
  }

  @Test
  public void testAnnotatedInstanceMethodParameterBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("@BitFlip annotation for instance method parameters test");
      int d = instanceMethod1(0);
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  public int instanceMethod2(int bar) {
    return bar;
  }

  @Test
  public void testCommandLineSpecifiedInstanceMethodParameterBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.params=foo",
                "+bitflip.foo.method=gov.nasa.jpf.test.mc.data.BitFlipTest.instanceMethod2(int)",
                "+bitflip.foo.name=bar",
                "+bitflip.foo.nbit=1")){
      System.out.println("command line specified bit flip for instance method parameters test");
      int d = instanceMethod2(0);
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @BitFlip
  int annotatedInstanceField;

  @Test
  public void testAnnotatedInstanceFieldBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("@BitFlip annotation for instance field test");
      annotatedInstanceField = 0;
      System.out.print("annotatedInstanceField = ");
      System.out.println(annotatedInstanceField);
      checkBitFlip(annotatedInstanceField);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  int instanceField;

  @Test
  public void testCommandLineSpecifiedInstanceFieldBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.fields=foo",
                "+bitflip.foo.field=gov.nasa.jpf.test.mc.data.BitFlipTest.instanceField")){
      System.out.println("command line specified bit flip for instance field test");
      instanceField = 0;
      System.out.print("instanceField = ");
      System.out.println(instanceField);
      checkBitFlip(instanceField);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @BitFlip
  static int annotatedStaticField;

  @Test
  public void testAnnotatedStaticFieldBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("@BitFlip annotation for static field test");
      annotatedStaticField = 0;
      System.out.print("annotatedStaticField = ");
      System.out.println(annotatedStaticField);
      checkBitFlip(annotatedStaticField);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  static int staticField;

  @Test
  public void testCommandLineSpecifiedStaticFieldBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.fields=foo",
                "+bitflip.foo.field=gov.nasa.jpf.test.mc.data.BitFlipTest.staticField",
                "+bitflip.foo.nbit=1")){
      System.out.println("command line specified bit flip for static field test");
      staticField = 0;
      System.out.print("staticField = ");
      System.out.println(staticField);
      checkBitFlip(staticField);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @Test
  public void testAnnotatedLocalVariableBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("@BitFlip annotation for local variable test");
      @BitFlip int d = 0;
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @Test
  public void testCommandLineSpecifiedLocalVariableBitFlip() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.localvars=bar",
                "+bitflip.bar.method=gov.nasa.jpf.test.mc.data.BitFlipTest.testCommandLineSpecifiedLocalVariableBitFlip()",
                "+bitflip.bar.name=d")){
      System.out.println("command line specified bit flip for local variable test");
      int d = 0;
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @Test
  public void testCommandLineOptionSuppressesAnnotation() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.localvars=foobar",
                "+bitflip.foobar.method=gov.nasa.jpf.test.mc.data.BitFlipTest.testCommandLineOptionSuppressesAnnotation()",
                "+bitflip.foobar.name=d")){
      System.out.println("command line option suppresses annotation test");
      @BitFlip(2) int d = 0;
      // though the annotation wants to flip 2 bits of d, the command line argument specifies to only flip 1 bit (by default)
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }

  @Test
  public void testCombineCommandLineOptionAndAnnotation() {

    if (!isJPFRun()){
      for (int i = 0; i < 32 * 32; ++i) {
        Verify.setBitInBitSet(0, i, false);
      }
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.localvars=foobar",
                "+bitflip.foobar.method=gov.nasa.jpf.test.mc.data.BitFlipTest.testCombineCommandLineOptionAndAnnotation()",
                "+bitflip.foobar.name=d")){
      System.out.println("combine command line option and annotation test");
      int d = 0;
      System.out.print("d = ");
      System.out.println(d);
      annotatedStaticField = 0;
      System.out.print("annotatedStaticField = ");
      System.out.println(annotatedStaticField);
      // check that 32 * 32 cases are all covered exactly once
      int bitNumber1 = 0, bitNumber2 = 0;
      for (int i = 0; i < 32; ++i) {
        if (d == (1 << i)) {
          bitNumber1 = i;
        }
        if (annotatedStaticField == (1 << i)) {
          bitNumber2 = i;
        }
      }
      assert Verify.getBitInBitSet(0, 32 * bitNumber1 + bitNumber2) == false;
      Verify.setBitInBitSet(0, 32 * bitNumber1 + bitNumber2, true);
    } else {
      for (int i = 0; i < 32 * 32; ++i) {
        assert Verify.getBitInBitSet(0, i) == true;
      }
    }
  }

  @Test
  public void testTwoCommandLineOptions() {

    if (!isJPFRun()){
      for (int i = 0; i < 32 * 32; ++i) {
        Verify.setBitInBitSet(0, i, false);
      }
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener",
                "+bitflip.localvars=foo,bar",
                "+bitflip.foo.method=gov.nasa.jpf.test.mc.data.BitFlipTest.testTwoCommandLineOptions()",
                "+bitflip.foo.name=d1",
                "+bitflip.bar.method=gov.nasa.jpf.test.mc.data.BitFlipTest.testTwoCommandLineOptions()",
                "+bitflip.bar.name=d2")){
      System.out.println("two command line options test");
      int d1 = 0;
      System.out.print("d1 = ");
      System.out.println(d1);
      int d2 = 0;
      System.out.print("d2 = ");
      System.out.println(d2);
      // check that 32 * 32 cases are all covered exactly once
      int bitNumber1 = 0, bitNumber2 = 0;
      for (int i = 0; i < 32; ++i) {
        if (d1 == (1 << i)) {
          bitNumber1 = i;
        }
        if (d2 == (1 << i)) {
          bitNumber2 = i;
        }
      }
      assert Verify.getBitInBitSet(0, 32 * bitNumber1 + bitNumber2) == false;
      Verify.setBitInBitSet(0, 32 * bitNumber1 + bitNumber2, true);
    } else {
      for (int i = 0; i < 32 * 32; ++i) {
        assert Verify.getBitInBitSet(0, i) == true;
      }
    }
  }

  @Test
  public void testTwoAnnotations() {

    if (!isJPFRun()){
      for (int i = 0; i < 32 * 32; ++i) {
        Verify.setBitInBitSet(0, i, false);
      }
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      System.out.println("two annotations test");
      annotatedStaticField = 0;
      System.out.print("annotatedStaticField = ");
      System.out.println(annotatedStaticField);
      annotatedInstanceField = 0;
      System.out.print("annotatedInstanceField = ");
      System.out.println(annotatedInstanceField);
      // check that 32 * 32 cases are all covered exactly once
      int bitNumber1 = 0, bitNumber2 = 0;
      for (int i = 0; i < 32; ++i) {
        if (annotatedStaticField == (1 << i)) {
          bitNumber1 = i;
        }
        if (annotatedInstanceField == (1 << i)) {
          bitNumber2 = i;
        }
      }
      assert Verify.getBitInBitSet(0, 32 * bitNumber1 + bitNumber2) == false;
      Verify.setBitInBitSet(0, 32 * bitNumber1 + bitNumber2, true);
    } else {
      for (int i = 0; i < 32 * 32; ++i)
        assert Verify.getBitInBitSet(0, i) == true;
    }
  }

  @Test
  public void testFlipTwoBits() {

    if (!isJPFRun()){
      for (int i = 0; i < (32 * 31) / 2; ++i) {
        Verify.setBitInBitSet(0, i, false);
      }
    }

    if (verifyNoPropertyViolation("+listener=gov.nasa.jpf.listener.BitFlipListener")){
      @BitFlip(2) int d;
      d = 0;
      System.out.print("d = ");
      System.out.println(d);
      // check that C(32, 2) cases are all covered exactly once
      int bitNumber1 = -1, bitNumber2 = -1, tmp = d;
      for (int i = 0; i < 32; ++i) {
        if ((d >> i & 1) == 1) {
          if (bitNumber1 == -1) {
            bitNumber1 = i;
          } else {
            bitNumber2 = i;
          }
          tmp ^= (1 << i);
        }
      }
      assert bitNumber1 != -1 && bitNumber2 != -1 && tmp == 0;
      int case_number = bitNumber2 * (bitNumber2 - 1) / 2 + bitNumber1;
      assert Verify.getBitInBitSet(0, case_number) == false;
      Verify.setBitInBitSet(0, case_number, true);
    } else {
      for (int i = 0; i < (32 * 31) / 2; ++i)
        assert Verify.getBitInBitSet(0, i) == true;
    }
  }

  @Test
  public void testBitFlipAPI() {

    if (!isJPFRun()){
      Verify.resetCounter(0);
    }

    if (verifyNoPropertyViolation()){
      System.out.println("getBitFlip API test");
      int d = Verify.getBitFlip(0, 1);
      System.out.print("d = ");
      System.out.println(d);
      checkBitFlip(d);
    } else {
      assert Verify.getCounter(0) == -1;
    }
  }
}
