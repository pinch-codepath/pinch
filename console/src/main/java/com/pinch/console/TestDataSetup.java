package com.pinch.console;

import java.io.IOException;
import java.text.ParseException;

import static com.pinch.console.TestUtil.Address;
import static com.pinch.console.TestUtil.Skills;
import static com.pinch.console.TestUtil.deleteAllEvents;
import static com.pinch.console.TestUtil.deleteAllOrgs;
import static com.pinch.console.TestUtil.insertEvent;
import static com.pinch.console.TestUtil.insertOrg;

public class TestDataSetup {

    public static void main(String[] args) throws IOException, ParseException {
        deleteAllEvents();

        deleteAllOrgs();

        Address a1 = new Address ("550 Polk St", "San Francisco", "CA", 94102, "Civic Center");
        Address a2 = new Address ("155 9th st", "San Francisco", "CA", 94103, "SOMA");
        Address a3 = new Address ("201 8th Street", "San Francisco", "CA", 94103, "SOMA");
        Address a4 = new Address ("480 Ellis St", "San Francisco", "CA", 94102, "Tenderloin");

        Skills s1 = new Skills ("Cooking", "Cleaning", "Changing Diapers");

        long orgId1 = insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f,
                "4158852274", "http://curryseniorcenter.org/how-to-help/volunteer/");
        insertEvent(orgId1,
                "Serve Breakfast To The Elderly",
                "One to five volunteers are needed each day to assist kitchen staff in serving breakfast and lunch. The breakfast hours are " +
                        "6:30 am to 9:00 am. The lunch hours are 10:00 am to 1:30 pm. You may choose to do either one or both shifts on the " +
                        "day of your choice.",
                "11/08/2015 06:30:00",
                "11/08/2015 09:00:00",
                a1, s1, "http://lh3.googleusercontent.com/Z0FEe6qtHrX1KxNMBGRZIYr2jRmQSVxmWqf_lUvASEFrldFD0HCM5tiy_zmUl3-9VDewilgBNmmwCgFHCmHPGg");

        long orgId2 = insertOrg("Next Door Shelter", "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103",
                37.776814f, -122.412128f, "4085017550","http://ecs-sf.org/getinvolved/volunteer.html");
        insertEvent(orgId2,
                "Serve Breakfast To The Elderly",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/02/2015 07:00:00",
                "11/02/2015 09:00:00",
                a3, s1, "http://lh3.googleusercontent.com/hvfc_io5Zs7m3qV-HjChEfyZqifeguEmAsAvrCF2HaMLtFyi1wkf3739YCJKIpthNDoVelaRTVJvIplA9rOfZ94");
        insertEvent(orgId2,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/07/2015 16:30:00",
                "11/07/2015 18:00:00",
                a1, s1, "http://lh3.googleusercontent.com/vi3aUHamuN_tySGGyavaFRBF61TgASkNXGLL-QL5cHuntSxXWLepRGpIivLPeeZsGBs__ozYZhGiJPzD8UAzrw");

        long orgId3 = insertOrg("Sanctuary Center",
                "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103",
                37.776814f, -122.412128f, "4154873300","http://ecs-sf.org/getinvolved/volunteer.html");
        insertEvent(orgId3,
                "Community Cleanup",
                "Join us for a huge Community Cleanup Day in West Oakland. Projects may include: Garden planting, Park renovation, Painting, " +
                        "Graffiti removal, Litter removal, Weeding, Illegal dumping collection, Breakfast snacks and liquids will be provided. " +
                        "We recommend bringing your own refillable bottle.",
                "11/02/2015 13:00:00",
                "11/02/2015 16:00:00",
                a2, s1, "http://lh3.googleusercontent.com/xoS08_ZgOV9Fv3p2_qqVdgvGXhGnDO59mGpvc_MksIADCzavTrNc2BSHV5Nim13sEyt4wS1Qg1zEpH9WhUDk_w");

        insertEvent(orgId3,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/03/2015 16:30:00",
                "11/03/2015 18:00:00",
                a3, s1, "http://lh3.googleusercontent.com/le-3hylQ9cr1rR2HWfXLKOrFTsI95rBWZ30i9AIU9IeO8Gs1cOZ-yJ14MXhdpdbReIn_4J_bpBOQKrjEn7FNyA");

        long orgId4 = insertOrg("Glide Memorial",
                "330 Ellis Street, San Francisco, CA 94102", 37.785268f, -122.411432f,
                "4156746000","http://www.glide.org/serveameal");
        insertEvent(orgId4,
                "Help setup event for school",
                "Join other volunteers and help K to College prepare for their 5th Annual Assembly Event. Set-up will take place at the International House in the Chevron Auditorium. We will set-up tables, chairs, signage, and boxes in preparation for the next day where volunteers will assemble 3,500 school supply kits and dental kits that will be given to every child eligible for the free & reduced lunch plan.",
                "11/02/2015 07:00:00",
                "11/02/2015 09:00:00",
                a1, s1, "http://lh3.googleusercontent.com/AEaX6bVOe9Taxry7YdaOxC87mjmd6tyElSH1aqBgM2UrHlseho-Ts3yTSwaday_4cParwfweVXrjPVCi85Lo");

        insertEvent(orgId4,
                "Serve Lunch",
                "Serving a meal in the Daily Free Meals Program can be a transformative experience. This program requires 85 volunteers each day to fill the breakfast, prep, lunch and dinner shifts, 364 days a year. With your help we can serve up to 2400 meals per day to our community. Volunteers assist with everything from serving food and bussing tables, to handing out silverware and condiments. Be prepared to roll up your sleeves, break a sweat, and make some beautiful human connections!",
                "11/07/2015 11:00:00",
                "11/07/2015 14:30:00",
                a2, s1, "http://lh3.googleusercontent.com/qZFFWg0usX3gEvqokrg4Pf73o_CQCfWqPuAZ_ZJ7JzCtkaBAhBHJePhig3FrscErn3irUNmotCutOT_0S3tG6w");

        long orgId5 = insertOrg("SF Marin Food Bank",
                "900 Pennsylvania Avenue San Francisco, CA 94107",
                37.754461f, -122.393679f,
                "4152821900","http://www.sfmfoodbank.org/volunteer-opportunities");
        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/02/2015 09:00:00",
                "11/02/2015 12:00:00",
                a2, s1, "http://lh3.googleusercontent.com/oPWpEt6A7IilepNU5dUKN2n7E_o0XP7VrSyq8oJz4z08hOD1XYoDOquWUj6-k6I15iT4ZWOX-gN5SIyVlsC9rQ");

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/06/2015 18:00:00",
                "11/06/2015 20:00:00",
                a1, s1, "http://lh3.googleusercontent.com/8xGCf3pO5wbrS4Em97b92bbY9gcPMRDDrn8JKIqcgexnIUO5w7POtbxvh0MntKnoPRsR0ThyQdjAalqOLo5IVw");

        long orgId6 = insertOrg("Micro Mentor", "Mercy Corps 45 SW Ankeny Street Portland, OR 97204",
                45.522617f, -122.670751f,
                "5034654181", "http://www.micromentor.org/");

        insertEvent(orgId6,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/05/2015 16:30:00",
                "11/05/2015 18:00:00",
                a4, s1, "http://lh3.googleusercontent.com/4uHC7PTQNy1ap-l1t5vPNo4nLqnI5jXkdl9yjKfi4MUHTo1JoxMDCweF_7Oc3jGBAiVwj5cPcPPVrBz2nO4JUw");

        long orgId7 = insertOrg("Girls on the run", "PO Box 510 Los Gatos, CA 95031", 37.160000f, -121.970000f, "4083541465", "http://www.gotrsv.org/");
        Address a5 = new Address ("3500 Amber Drive", "San Jose", "CA", 95117, "San Jose");
        insertEvent(orgId7,
                "4K Coach",
                "Warm-up your spirit fingers, lace up your sneakers and inspire a group of girls to be strong and healthy. Teams of volunteer coaches facilitate our easy to follow curriculum with groups of 8-15 girls over the course of 10-12 weeks. Amidst the conversation, laughter, hugs and energy awards, you will witness transformational change in the girls and don’t be surprised if you realize that you are positively changing as well!",
                "11/05/2015 16:30:00",
                "11/05/2015 18:00:00",
                a5, s1, "http://lh3.googleusercontent.com/DoLxhpdJeqYT1FsLm5hhBxBSv3M9DOeCvlJs0TidpxHbIx7uhoHNl80YvxYIXuWQ1X7sFTwgF54I1aEAcBKk");

        insertEvent(orgId7,
                "Running Buddy",
                "Ready. Set. Go! Lace up your sneakers, put on your GOTR gear and get ready for a 5k experience unlike any other. The excitement in the air is palpable as the girls meet their teammates, get their pictures taken, chant “Girls on the Run is so much fun” and squeeze in a final energy award before heading to the starting line.",
                "11/03/2015 16:30:00",
                "11/03/2015 18:00:00",
                a5, s1, "http://lh3.googleusercontent.com/LzlZ6bGvLL0UnBu0Bsgl8xYbGPsB7sLHs59YEiHppkQ0h2Mn3xSX429mlj4x3SFN2FWD5XVp7_wIjQfs33AHSIg");

        long orgId8 = insertOrg("First Graduate", "3130 20th Street, Suite 275 San Francisco, CA 94110", 37.160000f, -121.970000f, "4155613450", "http://firstgraduate.org/");
        Address a6 = new Address ("3500 Amber Drive", "San Jose", "CA", 95117, "San Jose");
        insertEvent(orgId8,
                "Career Exploration",
                "Warm-up your spirit fingers, lace up your sneakers and inspire a group of girls to be strong and healthy. Teams of volunteer coaches facilitate our easy to follow curriculum with groups of 8-15 girls over the course of 10-12 weeks. Amidst the conversation, laughter, hugs and energy awards, you will witness transformational change in the girls and don’t be surprised if you realize that you are positively changing as well!",
                "11/05/2015 16:30:00",
                "11/05/2015 18:00:00",
                a6, s1, "http://lh3.googleusercontent.com/hNLoYCThu19jaU_bXsRzisrbtjob56ENeDrl5VgCdX27v9nMaQ_9RirO2l5jl-OSE6J6nAUo5MtbSRpGS-wn53w");

        insertEvent(orgId8,
                "After School Tutoring",
                "During their time in middle school, students are exposed to the breadth of career possibilities available to someone with a college degree to expand their thinking about possibilities for the future.",
                "11/04/2015 16:00:00",
                "11/04/2015 18:00:00",
                a6, s1, "http://lh3.googleusercontent.com/ulP8kn8PuZbmpCBI-xDpMVSYeWEry5y2zH7g9xEkcJwoQumCUExTACxTiXu0FgId3p98YxUXAwqv3OGdlliK");

        long orgId9 = insertOrg("SF Goodwill", "1500 Mission St, San Francisco, CA  94103", 37.774374f, -122.417837f, "4155752101", "http://sfgoodwill.org/");
        Address a7 = new Address ("1500 Mission St", "San Francisco", "CA", 94103, "San Francisco");
        insertEvent(orgId9,
                "Job Center Coaching",
                "At our CAP Career Center, single or group volunteers prepare job seekers for work by conducting mock interview training, assisting with online job applications, and providing feedback and recommendations to improve resumes. Learn more about how the CAP Career Center opens doorways to jobs for local people in need.",
                "11/06/2015 10:30:00",
                "11/06/2015 14:00:00",
                a7, s1, "http://lh3.googleusercontent.com/FwdDQ1alSjWqJsQZgIw3gSwca0xpqOb5t5HtteQKj_wRwhnbpZ0qs-TNjv2ksmMsqYBkjn3BoRHktHLAFAWkb_w");

        insertEvent(orgId9,
                "Retail Support",
                "Our 21 stores in Marin, San Francisco, and San Mateo County are eager for assistance in the basics of store maintenance and organization, including merchandise replenishment, merchandise out-dating, customer greeting, and dusting and cleaning. More than 80% of the funding for our job training and placement services comes from our retail stores.",
                "11/07/2015 16:00:00",
                "11/07/2015 18:00:00",
                a7, s1, "http://lh3.googleusercontent.com/3bCdk1g_C8EvuEJsdeUGZHpFv84i2tx7deKmINE7MmDam0SLkaPpCUGW4t0eTN1261Rn00HOa91PBveJuCGhDA");

    }

}
