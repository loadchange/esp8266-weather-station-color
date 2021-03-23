/**The MIT License (MIT)
 
 Copyright (c) 2018 by ThingPulse Ltd., https://thingpulse.com
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
#ifndef SETTINGS_H
#define SETTINGS_H
#include <Arduino.h>
#include "TZinfo.h"

// Setup
String WIFI_SSID = "nbody";
String WIFI_PASS = "tygsjtygmx401";
#define WIFI_HOSTNAME "ThingPulse-weather-station-color"

const int UPDATE_INTERVAL_SECS = 10 * 60; // Update every 10 minutes
const int SLEEP_INTERVAL_SECS = 0;        // Going to sleep after idle times, set 0 for insomnia
const boolean HARD_SLEEP = false;         // true go into deepSleep false = turn Back light off

// OpenWeatherMap Settings
// Sign up here to get an API key: https://docs.thingpulse.com/how-tos/openweathermap-key/
String OPEN_WEATHER_MAP_API_KEY = "5744140976e2674863694a5b231c17de";
/*
Go to https://openweathermap.org/find?q= and search for a location. Go through the
result set and select the entry closest to the actual location you want to display 
data for. It'll be a URL like https://openweathermap.org/city/2657896. The number
at the end is what you assign to the constant below.
 */
String OPEN_WEATHER_MAP_LOCATION_ID = "1816670";
String DISPLAYED_LOCATION_NAME = "Beijing";
/*
Arabic -> ar, Bulgarian -> bg, Catalan -> ca, Czech -> cz, German -> de, Greek -> el,
English -> en, Persian (Farsi) -> fa, Finnish -> fi, French -> fr, Galician -> gl,
Croatian -> hr, Hungarian -> hu, Italian -> it, Japanese -> ja, Korean -> kr,
Latvian -> la, Lithuanian -> lt, Macedonian -> mk, Dutch -> nl, Polish -> pl,
Portuguese -> pt, Romanian -> ro, Russian -> ru, Swedish -> se, Slovak -> sk,
Slovenian -> sl, Spanish -> es, Turkish -> tr, Ukrainian -> ua, Vietnamese -> vi,
Chinese Simplified -> zh_cn, Chinese Traditional -> zh_tw.
*/
const String OPEN_WEATHER_MAP_LANGUAGE = "zh_cn";

// Adjust according to your language
const String WDAY_NAMES[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
const String MONTH_NAMES[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
const String SUN_MOON_TEXT[] = {"Sun", "Rise", "Set", "Moon", "Age", "Illum"};
const String MOON_PHASES[] = {"New Moon", "Waxing Crescent", "First Quarter", "Waxing Gibbous",
                              "Full Moon", "Waning Gibbous", "Third quarter", "Waning Crescent"};

// pick one from TZinfo.h
String TIMEZONE = getTzInfo("Asia/Shanghai");

// values in metric or imperial system?
bool IS_METRIC = true;

// Change for 12 Hour/ 24 hour style clock
bool IS_STYLE_12HR = false;

// Change for HH:MM/ HH:MM:SS format clock
bool IS_STYLE_HHMM = false; // true => HH:MM

// change for different NTP (time servers)
#define NTP_SERVERS "ntp1.aliyun.com"
// #define NTP_SERVERS "us.pool.ntp.org", "time.nist.gov", "pool.ntp.org"

// August 1st, 2018
#define NTP_MIN_VALID_EPOCH 1533081600

// Pins for the ILI9341
#define TFT_CS D2
#define TFT_DC D4
#define TFT_MOSI D7
#define TFT_SCLK D5
#define TFT_RST D3
#define TFT_MISO D6

#define TFT_LED D1


#define HAVE_TOUCHPAD
#define TOUCH_CS D0
#define TOUCH_IRQ A0

/***************************
 * End Settings
 **************************/
#endif
 //  ILI9341_SPI(int8_t _CS, int8_t _DC, int8_t _MOSI, int8_t _SCLK,
    //    int8_t _RST, int8_t _MISO);