/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.steps;

import hudson.model.JDK;
import org.jenkinsci.plugins.structs.SymbolLookup;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.JenkinsRule;

public class ToolStepTest {

    @Rule public JenkinsRule r = new JenkinsRule();

    @Test public void configRoundTrip() throws Exception {
        String name = "My JDK";
        JDK.DescriptorImpl desc = r.jenkins.getDescriptorByType(JDK.DescriptorImpl.class);
        String type = desc.getId();
        r.jenkins.getJDKs().add(new JDK(name, "/wherever"));
        ToolStep s = new StepConfigTester(r).configRoundTrip(new ToolStep(name));
        assertEquals(name, s.getName());
        assertEquals(null, s.getType());
        s.setType(type);
        s = new StepConfigTester(r).configRoundTrip(s);
        assertEquals(name, s.getName());
        if (SymbolLookup.getSymbolValue(desc).isEmpty()) {
            assertEquals(type, s.getType());
        } // else (Jenkins 2.x) StepConfigTester does not make sense since in real life we would not read an existing value (an ID) into a pulldown listing only symbols
    }

}