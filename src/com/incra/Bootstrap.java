package com.incra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.util.ConfigHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.incra.domain.Activity;
import com.incra.domain.ActivityCategory;
import com.incra.domain.Badge;
import com.incra.domain.Challenge;
import com.incra.domain.Country;
import com.incra.domain.Goal;
import com.incra.domain.GoalActivity;
import com.incra.domain.Level;
import com.incra.domain.OrganizationType;
import com.incra.domain.OrganizationTypeActivity;
import com.incra.domain.Question;
import com.incra.domain.QuestionCategory;
import com.incra.domain.TimeZone;
import com.incra.domain.User;
import com.incra.domain.UserBadge;
import com.incra.domain.UserChallenge;
import com.incra.domain.enums.QuestionType;

/**
 * The <i>Bootstrap</i> class is a servletContextListener that initializes the
 * database content for the application.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
public class Bootstrap implements ServletContextListener {

    protected SessionFactory sessionFactory;
    protected Session session;
    protected PasswordEncoder passwordEncoder;

    public Bootstrap() {
    }

    @SuppressWarnings("unused")
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext.xml" });

        sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        session = sessionFactory.openSession();
        passwordEncoder = (PasswordEncoder) appContext.getBean("passwordEncoder");

        // Country seeds
        Number countryCount = (Number) session.createCriteria(Country.class)
                .setProjection(Projections.rowCount()).uniqueResult();

        if (countryCount.intValue() == 0) {
            loadSetupFile(appContext, "01-Countries.sql");
        }

        // TimeZone seeds
        TimeZone timeZoneGMT = buildTimeZone("GMT", "London", 0);
        TimeZone timeZoneEST = buildTimeZone("EST", "New york", 5);
        TimeZone timeZoneCST = buildTimeZone("CST", "Chicago", 6);
        TimeZone timeZoneMST = buildTimeZone("MST", "Denver", 7);
        TimeZone timeZonePST = buildTimeZone("PST", "Los Angeles", 8);
        TimeZone timeZoneHST = buildTimeZone("HST", "Hawaii", 11);

        // OrganizationType seeds
        OrganizationType otIndividual = buildOrganizationType("Individual",
                "A family, a person, a student");
        OrganizationType otSmallMedCorp = buildOrganizationType("Small or Medium Size Business",
                "A business typically with less than 500 employees");
        OrganizationType otBigCorp = buildOrganizationType("Big Corporation",
                "A business typically with more than 500 employees");
        OrganizationType otSchool = buildOrganizationType("School", "A school within a district");
        OrganizationType otSchoolDist = buildOrganizationType("School District",
                "A entire school district");
        OrganizationType otGovernment = buildOrganizationType("Government",
                "A government agency, or worker within a government agency");

        // Activity Category seeds
        ActivityCategory acPhysical = buildActivityCategory("Physical", "Physical", "1Desc");
        ActivityCategory acWellness = buildActivityCategory("Wellness", "Wellness", "2Desc");

        // Question Category seeds
        QuestionCategory qcPhysical = buildQuestionCategory("Physical", "Recycling", "1Desc");
        QuestionCategory qcWellness = buildQuestionCategory("Wellness", "Wellness", "2Desc");

        // Goal seeds
        Goal goalCarFit = buildGoal("Cardio Fit", "Take care of that ticker!");
        Goal goalWeiLos = buildGoal("Weight Loss", "Reduce by a size or two");
        Goal goalChoRed = buildGoal("Cholestrol Reduction", "Keep those arteries clean!");
        Goal goalFatBur = buildGoal("Fat Burn", "Take off those extra pounds");
        Goal goalMusTon = buildGoal("Muscle Tone", "Firm up those muscles");
        Goal goalOveFit = buildGoal("Overall Fitness",
                "Start to feel and look like a healthy person");

        // Activity seeds
        Activity activity;

        activity = buildActivity("Developer Dips", acPhysical, 3, "CIMG0002", "CIMG0001",
                "You can do this sitting at your desk.  Place your hands on your armrests "
                        + "or the sides of your chair.  Push up using your upper arms. "
                        + "Count to ten.  Win points for sucking in your gut.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalMusTon, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 2);

        activity = buildActivity("Laptop Lifts", acPhysical, 2, "CIMG0148", "CIMG0145",
                "Take that laptop and lift it to the sky! "
                        + " Bonus points for 17-inch and larger monitors.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Native Code Naps", acWellness, 1, "CIMG0077", null,
                "The exercise value of naps is underrated in today's world.  "
                        + "Your computer gets downtime, why shouldn't you?");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 2);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Sales Stretches", acPhysical, 3, "CIMG0055", "CIMG0056",
                "Give yourself a boost to reach that quota. "
                        + "Stretch out your right leg to the side, "
                        + "try to reach your toes. Switch sides.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 2);
        this.buildGoalActivity(goalWeiLos, activity, 3);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Marketing Meditations", acWellness, 2, "CIMG0057", null,
                "Find a quiet location with a comfortable seat.  "
                        + "Relax your mind and remember to take a deep breath in and out.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalChoRed, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 2);

        activity = buildActivity("Linux Leaps", acPhysical, 2, "CIMG0058", "CIMG0059",
                "Release your inner ninja by finding an object that you can leap over "
                        + "(Caution: Advanced qubies only!)");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);
        this.buildGoalActivity(goalMusTon, activity, 1);

        activity = buildActivity("PHP Planks", acPhysical, 4, "CIMG0884", null,
                "Planking is hard work.  This is an unexpectedly difficult abdominal exercise. "
                        + "Maintain the position for one full minute. "
                        + " Do this ten times.  Take a picture and email it to us.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Java Detox", acWellness, 1, "CIMG0062", null,
                "Skip the coffee or soda machine and toast your colleagues with a glass of water.  "
                        + "Water is great for weight loss, so sometimes you might "
                        + "think you are hungry when you actually are thirsty.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Run on Rails", acPhysical, 3, "CIMG0060", null,
                "Find some funky music and a sturdy office desk.  Hop on the desk.  "
                        + "Run in place to the rhythm of the music.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalChoRed, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);
        this.buildGoalActivity(goalMusTon, activity, 1);

        activity = buildActivity("SQL Squats", acPhysical, 4, "CIMG0061", null,
                "Do this as a challenge with your co-workers.  "
                        + "Try to see who can hold this pose the longest.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 2);

        activity = buildActivity("Throughput Thigh Stretches", acPhysical, 2, "CIMG0073", null,
                "While leading your next code review, "
                        + "get double the value by stretching those thighs and legs.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Debugging Dunks", acPhysical, 2, "CIMG0065", null,
                "Tone your arm while recycling trash around the office. "
                        + " Stand 3 or 4 feet away from the recycling bin, aim, and shoot!"
                        + " Challenge your qubemate for the 3 pointer.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalWeiLos, activity, 2);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Compile-time Climbs", acPhysical, 2, "CIMG0069", "CIMG0070",
                "See you if can run up one stair and then down another in less time "
                        + "than one more build. "
                        + "Take the elevator while regression tests are running.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalWeiLos, activity, 1);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 1);
        this.buildGoalActivity(goalMusTon, activity, 1);

        activity = buildActivity("Scanning the Room, not your Code", acWellness, 1, "CIMG0042",
                "CIMG0043", "Eyes that scan lines of code all day need a break.  "
                        + "Let your eyes scan around the edges of objects in the room.  "
                        + "Do this for 3 minutes -- or until someone notices.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalOveFit, activity, 1);

        activity = buildActivity("Swan Dives", acPhysical, 3, "CIMG0071", null,
                "This is a hidden exercise gem inside your office's handicap bathroom.  "
                        + "There's a metal bar that you can use for a variety of stretches.  "
                        + "Try to dive but don't forget where your head is!");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 1);
        this.buildGoalActivity(goalFatBur, activity, 1);
        this.buildGoalActivity(goalOveFit, activity, 2);

        activity = buildActivity("Tech Crunches", acPhysical, 4, "CIMG0126", "CIMG0127",
                "Sit in your chair, and pull your legs up tight. "
                        + " As you push your legs back, imagine that you "
                        + "are ahead on your development sprint.");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalCarFit, activity, 2);
        this.buildGoalActivity(goalOveFit, activity, 1);
        this.buildGoalActivity(goalMusTon, activity, 1);

        activity = buildActivity("Breakpoint Balances", acPhysical, 3, "CIMG0068", null,
                "Imagine that a soda can is a bug to be squashed. "
                        + " How long can you maintain your balance on top of it?");
        this.buildOrganizationTypeActivity(otIndividual, activity);
        this.buildOrganizationTypeActivity(otSmallMedCorp, activity);
        this.buildOrganizationTypeActivity(otBigCorp, activity);
        this.buildOrganizationTypeActivity(otSchoolDist, activity);
        this.buildOrganizationTypeActivity(otSchool, activity);
        this.buildOrganizationTypeActivity(otGovernment, activity);
        this.buildGoalActivity(goalOveFit, activity, 2);
        this.buildGoalActivity(goalMusTon, activity, 1);

        // Questions
        buildQuestion(qcPhysical, QuestionType.Boolean, "Do you work out?", 5, "  ", "  ");
        buildQuestion(qcPhysical, QuestionType.Boolean, "Do you avoid fatty foods?", 10, "  ", "  ");
        buildQuestion(qcPhysical, QuestionType.Boolean, "Do you watch your diet?", 5, "  ", "  ");
        buildQuestion(qcPhysical, QuestionType.Boolean,
                "Did you know that fortune cookies are good for you?", 2, "  ", "  ");
        buildQuestion(qcPhysical, QuestionType.Boolean,
                "Do you go to the gym more than twice per week?", 5, "  ", "  ");
        buildQuestion(qcPhysical, QuestionType.Boolean,
                "Do you take the stairs instead of the elevator?", 5, "  ", "  ");

        // Badge seeds
        Badge badgeLifter = buildBadge("Laptop Lifter", "/badge/lifter.jpg",
                "You've lifted your share of laptops");
        Badge badgeDancer = buildBadge("Desktop Dancer", "/badge/dancer.jpg",
                "There are footprints on your desk");
        Badge badgeDunker = buildBadge("Super Dunker", "/badge/dunker.jpg",
                "You've dunked the trash from 10 feet");
        Badge badgePlanker = buildBadge("Planker", "/badge/plank.jpg",
                "You are a star at the PHP plank");
        Badge badgeSteady = buildBadge("Steady Visitor", "/badge/repeat5.jpg",
                "You've signed-in each day for a week");
        Badge badgeMulTal = buildBadge("Multi-Talented", "/badge/multi.jpg",
                "You've completed more than four activities each day for a week");

        // Level seeds
        Level levelNewbie = buildLevel("Newbie", "a", 0, "/level/newbie.jpg",
                "Welcome to QbeFit!  Enjoy staying fit at your desk!");
        Level levelJourneyman = buildLevel("Journeyman", "a", 40, "/level/journeyman.jpg",
                "You're becoming a real Qbie!");
        Level levelClimber = buildLevel("Climber", "a", 60, "/level/climber.jpg",
                "Keep advancing on your goals!");
        Level levelAchiver = buildLevel("Achiver", "an", 100, "/level/achiever.jpg",
                "You're doing cool things, keep going!");
        Level levelStar = buildLevel("Star", "a", 200, "/level/star.jpg",
                "Everyone looks up to you!");
        Level levelSuperstar = buildLevel("Superstar", "a", 500, "/level/superstar.jpg",
                "Your star is shining brightly!");

        // Challenges
        Challenge challenge01 = buildChallenge("Desk Hoppers",
                "Complete Linux Leaps 5 times in a row", activity, 5);
        Challenge challenge02 = buildChallenge("De-stressed Qbies",
                "Complete Marketing Meditations 10 times", activity, 10);

        // Users
        User user01 = buildUser("admin@qbefit.com", "QbeFit", "Admin", "123456", timeZonePST, 26,
                levelJourneyman, true);
        User user02 = buildUser("angela@gmail.com", "Angela", "DuPree", "123456", timeZonePST, 30,
                levelJourneyman, false);
        User user03 = buildUser("george@gmail.com", "George", "Jetson", "123456", timeZonePST, 120,
                levelAchiver, false);
        User user04 = buildUser("thomas@verizon.net", "Thomas", "Yan", "123456", timeZonePST, 130,
                levelAchiver, false);
        User user05 = buildUser("robert@sbcglobal.net", "Robert", "Hill", "123456", timeZonePST,
                56, levelClimber, false);
        User user06 = buildUser("edward@gmail.com", "Edward", "Wood", "123456", timeZonePST, 20,
                levelNewbie, false);
        User user07 = buildUser("arianna@gmail.com", "Arianna", "Rockmore", "123456", timeZonePST,
                25, levelJourneyman, false);

        // Badges earned
        buildUserBadge(user01, badgeMulTal);
        buildUserBadge(user01, badgeLifter);
        buildUserBadge(user02, badgeSteady);
        buildUserBadge(user03, badgePlanker);
        buildUserBadge(user05, badgeDancer);
        buildUserBadge(user07, badgeDunker);
        buildUserBadge(user07, badgeSteady);
        buildUserBadge(user07, badgeMulTal);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @SuppressWarnings("deprecation")
    protected void loadSetupFile(ApplicationContext appContext, String populateFileName) {
        try {
            System.out.println("Loading: " + populateFileName);

            InputStreamReader populateFileReader = null;
            InputStream stream = ConfigHelper.getResourceAsStream(populateFileName);
            populateFileReader = new InputStreamReader(stream);

            SessionFactory sf = (SessionFactory) appContext.getBean("sessionFactory");
            Session session = sf.openSession();
            Transaction transaction = session.beginTransaction();
            Connection connection = session.connection();
            Statement statement = connection.createStatement();

            BufferedReader reader = new BufferedReader(populateFileReader);
            String combinedSql = "";
            long lineNo = 0;

            String delimiter = ";";
            for (String sql = reader.readLine(); sql != null; sql = reader.readLine()) {
                try {
                    lineNo++;
                    if ((lineNo % 10) == 0)
                        System.out.println("populate: line " + lineNo);

                    if (sql.startsWith("DELIMITER")) {
                        delimiter = sql.substring(10);
                    } else {
                        String trimmedSql = sql.trim();
                        if (trimmedSql.length() == 0 || trimmedSql.startsWith("--")
                                || trimmedSql.startsWith("//") || trimmedSql.startsWith("/*")) {
                            continue;
                        } else {
                            // append it
                            combinedSql += " " + trimmedSql;

                            if (trimmedSql.endsWith(delimiter)) {
                                // process it
                                combinedSql = combinedSql.replace(delimiter, "");
                                statement.execute(combinedSql);
                                combinedSql = "";
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new JDBCException("Error during import script execution at line "
                            + lineNo, e);
                }
            }
            transaction.commit();
            statement.close();
            connection.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected TimeZone buildTimeZone(String id, String name, int relativeUTC) {
        Criteria criteria = session.createCriteria(TimeZone.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<TimeZone> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        TimeZone timeZone = new TimeZone();

        timeZone.setId(id);
        timeZone.setName(name);
        timeZone.setRelativeUTC(relativeUTC);
        session.save(timeZone);

        return timeZone;
    }

    protected OrganizationType buildOrganizationType(String name, String description) {
        Criteria criteria = session.createCriteria(OrganizationType.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<OrganizationType> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        OrganizationType organizationType = new OrganizationType();

        organizationType.setName(name);
        organizationType.setDescription(description);
        session.save(organizationType);

        return organizationType;
    }

    protected ActivityCategory buildActivityCategory(String name, String label, String description) {
        Criteria criteria = session.createCriteria(ActivityCategory.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<ActivityCategory> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        ActivityCategory activityCategory = new ActivityCategory();

        activityCategory.setName(name);
        activityCategory.setLabel(label);
        activityCategory.setDescription(description);
        session.save(activityCategory);

        return activityCategory;
    }

    protected QuestionCategory buildQuestionCategory(String name, String label, String description) {
        Criteria criteria = session.createCriteria(QuestionCategory.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<QuestionCategory> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        QuestionCategory questionCategory = new QuestionCategory();

        questionCategory.setName(name);
        questionCategory.setLabel(label);
        questionCategory.setDescription(description);
        session.save(questionCategory);

        return questionCategory;
    }

    protected Goal buildGoal(String name, String description) {
        Criteria criteria = session.createCriteria(Goal.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<Goal> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Goal goal = new Goal();

        goal.setName(name);
        goal.setDescription(description);
        session.save(goal);

        return goal;
    }

    protected Activity buildActivity(String name, ActivityCategory activityCategory,
            int difficulty, String photo1, String photo2, String description) {
        Criteria criteria = session.createCriteria(Activity.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<Activity> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Activity activity = new Activity();

        activity.setName(name);
        activity.setActivityCategory(activityCategory);
        activity.setDifficulty(difficulty);
        activity.setPhoto1(photo1);
        activity.setPhoto2(photo2);
        activity.setDescription(description);
        session.save(activity);

        return activity;
    }

    protected OrganizationTypeActivity buildOrganizationTypeActivity(
            OrganizationType organizationType, Activity activity) {
        Criteria criteria = session.createCriteria(OrganizationTypeActivity.class);
        criteria.add(Restrictions.eq("organizationType", organizationType));
        criteria.add(Restrictions.eq("activity", activity));
        @SuppressWarnings("unchecked")
        List<OrganizationTypeActivity> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        OrganizationTypeActivity orgTypeActivity = new OrganizationTypeActivity();

        orgTypeActivity.setOrganizationType(organizationType);
        orgTypeActivity.setActivity(activity);

        organizationType.addActivity(orgTypeActivity);
        session.flush();

        return orgTypeActivity;
    }

    protected GoalActivity buildGoalActivity(Goal goal, Activity activity, int multiplier) {
        Criteria criteria = session.createCriteria(GoalActivity.class);
        criteria.add(Restrictions.eq("goal", goal));
        criteria.add(Restrictions.eq("activity", activity));
        @SuppressWarnings("unchecked")
        List<GoalActivity> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        GoalActivity goalActivity = new GoalActivity();

        goalActivity.setGoal(goal);
        goalActivity.setActivity(activity);
        goalActivity.setMultiplier(multiplier);

        goal.addActivity(goalActivity);
        session.flush();

        return goalActivity;
    }

    protected Badge buildBadge(String name, String icon, String description) {
        Criteria criteria = session.createCriteria(Badge.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<Badge> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Badge badge = new Badge();

        badge.setName(name);
        badge.setIcon(icon);
        badge.setDescription(description);
        session.save(badge);

        return badge;
    }

    protected Level buildLevel(String name, String article, int points, String icon,
            String description) {
        Criteria criteria = session.createCriteria(Level.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<Level> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Level level = new Level();

        level.setName(name);
        level.setArticle(article);
        level.setPoints(points);
        level.setIcon(icon);
        level.setDescription(description);
        session.save(level);

        return level;
    }

    protected Challenge buildChallenge(String name, String description, Activity activity,
            int amount) {
        Criteria criteria = session.createCriteria(Challenge.class);
        criteria.add(Restrictions.eq("name", name));
        @SuppressWarnings("unchecked")
        List<Challenge> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Challenge challenge = new Challenge();

        challenge.setName(name);
        challenge.setDescription(description);
        session.save(challenge);

        // Build one challenge step, with the activity/amount parameters
        // TODO

        return challenge;
    }

    protected Question buildQuestion(QuestionCategory questionCategory, QuestionType questionType,
            String text, int points, String explanation, String answer) {
        Criteria criteria = session.createCriteria(Question.class);
        criteria.add(Restrictions.eq("questionCategory", questionCategory));
        criteria.add(Restrictions.eq("questionType", questionType));
        criteria.add(Restrictions.eq("text", text));
        @SuppressWarnings("unchecked")
        List<Question> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        Question question = new Question();

        question.setQuestionCategory(questionCategory);
        question.setQuestionType(questionType);
        question.setText(text);
        question.setPoints(points);
        question.setExplanation(explanation);
        question.setAnswer(answer);

        session.save(question);
        return question;
    }

    protected User buildUser(String email, String firstName, String lastName, String password,
            TimeZone timeZone, int points, Level level, boolean isAdmin) {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        @SuppressWarnings("unchecked")
        List<User> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        String encPassword = passwordEncoder.encodePassword(password, null);
        User user = new User();

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(encPassword);
        user.setAdmin(isAdmin);
        user.setTimeZone(timeZone);
        user.setPoints(points);
        user.setLevel(level);
        user.setLocked(false);
        user.setLoginCount(0);
        session.save(user);

        return user;
    }

    protected UserChallenge buildUserChallenge(User user, Challenge challenge) {
        Criteria criteria = session.createCriteria(UserChallenge.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("challenge", challenge));
        @SuppressWarnings("unchecked")
        List<UserChallenge> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        UserChallenge userChallenge = new UserChallenge();

        userChallenge.setUser(user);
        userChallenge.setChallenge(challenge);
        userChallenge.setDateCreated(new Date());
        session.save(userChallenge);

        return userChallenge;
    }

    protected UserBadge buildUserBadge(User user, Badge badge) {
        Criteria criteria = session.createCriteria(UserBadge.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("badge", badge));
        @SuppressWarnings("unchecked")
        List<UserBadge> existingRecords = criteria.list();

        if (existingRecords.size() > 0) {
            return existingRecords.get(0);
        }

        UserBadge userBadge = new UserBadge();

        userBadge.setUser(user);
        userBadge.setBadge(badge);
        userBadge.setDateCreated(new Date());
        session.save(userBadge);
        user.getUserBadges().add(userBadge);
        session.save(user);

        return userBadge;
    }
}
