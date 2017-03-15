#! /usr/bin/env python
# -*- coding: utf-8 -*-

import sys, os, argparse, re
from datetime import datetime
from com.dtmilano.android.viewclient import ViewClient

_s = 1
def main(argv):
    package = "se.attentec.attenhome"
    parser = argparse.ArgumentParser(description='Start Android scenarios')
    parser.add_argument('-rn', help="sets react as the platform", action="store_true")
    parser.add_argument('-android', help="sets android as the platform", action="store_true")
    parser.add_argument('-t', type=int, nargs='?', help="sets the test to be run [1...8]", choices=xrange(1,9), required=True)
    args = vars(parser.parse_args())
    if args['rn']:
        package = "com.attenhome"
    if args['t'] == 1:
        #EXPAND BOTH GRAPHS HOME SCREEN
        startApplication(package)
        expandHomeStats()
    elif args['t'] == 2:
        #EXPAND BOTH GRAPHS RADIATOR SCREEN
        startApplication(package)
        expandRadiatorStats()
    elif args['t'] == 3:
        #SELECT A DEVICE FLIP SWITCH, BACK FLIP SWITCH AGAIN
        startApplication(package)
        selectDeviceFlipSwitches(args['rn'])
    elif args['t'] == 4:
        #APP STARTUP
        startApplication(package)
    elif args['t'] == 5:
        #SELECT ROOM
        selectRoom()
    elif args['t'] == 6:
        #SELECT DEVICE
        selectDevice()
    elif args['t'] == 7:
        #SWITCH TO STATS TAB HOME SCREEN
        goToHomeStats()
    elif args['t'] == 8:
        #GO BACK FROM DEVICE SCREEN
        backFromDevice()
    else:
        print 'Test number was not valid (needs to be in [1...8])'
    platform = 'React Native' if args['rn'] else 'Android'
    print 'Test number ' + str(args['t']) + ' was completed on ' + platform + ' at ' + str(datetime.now().time())

def expandHomeStats():
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Statistics|STATISTICS')).touch()
    print("Pressed Stats tab at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Power consumption*')).touch()
    print("Expanded power consumption graph at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Temperature*')).touch()
    print("Expanded temperature graph at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def expandRadiatorStats():
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Kitchen').touch()
    print("Pressed kitchen at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Mounted radiator').touch()
    print("Pressed Mounted Radiator at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Statistics|STATISTICS')).touch()
    print("Pressed Stats tab at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Power consumption*')).touch()
    print("Expanded power consumption graph at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Temperature*')).touch()
    print("Expanded temperature graph at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def selectDeviceFlipSwitches(isReact):
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Kitchen').touch()
    print("Pressed kitchen at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Oven Lamp').touch()
    print("Pressed oven lamp at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    if isReact:
        switch = vc.findViewById('id/no_id/9')
    else:
        switch = vc.findViewById('se.attentec.attenhome:id/powerswitch')
    switch.touch()
    print("Flipped switch on device screen at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    device.press('KEYCODE_BACK')
    print("Pressed Back at " + str(datetime.now().time()))
    vc.sleep(_s)

    vc.dump(window=-1)
    if isReact:
        switch = vc.findViewById('id/no_id/11')
    else:
        switch = vc.findViewById('se.attentec.attenhome:id/list_power_switch')
    switch.touch()
    print("Flipped switch on room screen at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def startApplication(package):
    activity = '.MainActivity'
    component = package + "/" + activity
    #device.shell('am force-stop ' + package)
    device.startActivity(component=component)
    print("Started application at " + str(datetime.now().time()))
    vc.dump(window=-1)


def selectRoom():
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Kitchen').touch()
    print("Pressed kitchen at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def selectDevice():
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(u'Oven Lamp').touch()
    print("Pressed oven lamp at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def goToHomeStats():
    vc.dump(window=-1)
    vc.findViewWithTextOrRaise(re.compile('Statistics|STATISTICS')).touch()
    print("Pressed Stats tab at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

def backFromDevice():
    vc.dump(window=-1)
    device.press('KEYCODE_BACK')
    print("Pressed Back at " + str(datetime.now().time()))
    vc.dump(window=-1)
    vc.sleep(_s)

if __name__ == "__main__":
    serialno = '.*'
    kwargs1 = {'ignoreversioncheck': False, 'verbose': False, 'ignoresecuredevice': False, 'serialno': serialno}
    device, serialno = ViewClient.connectToDeviceOrExit(**kwargs1)
    kwargs2 = {'forceviewserveruse': False, 'useuiautomatorhelper': False, 'ignoreuiautomatorkilled': True, 'autodump': False, 'startviewserver': True, 'compresseddump': True}
    vc = ViewClient(device, serialno, **kwargs2)
    main(sys.argv[1:])
