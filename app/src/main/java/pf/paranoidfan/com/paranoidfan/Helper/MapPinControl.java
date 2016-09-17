package pf.paranoidfan.com.paranoidfan.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pf.paranoidfan.com.paranoidfan.Model.ChatModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupChatModel;
import pf.paranoidfan.com.paranoidfan.Model.PinDetailModel;
import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModel;
import pf.paranoidfan.com.paranoidfan.R;

/**
 * Created by KasperStar on 8/26/2016.
 */
public class MapPinControl {

    public static UserModel owner;

    public static void SetMapPinImageByType(ImageView pinImage, String pinType, String pinTag){
        switch(pinType){
            case PinTypeModel.Apparel:
                pinImage.setImageResource(R.mipmap.menu_apparel);
                break;
            case PinTypeModel.EntryExit:
                pinImage.setImageResource(R.mipmap.menu_entry);
                break;
            case PinTypeModel.Restroom:
                pinImage.setImageResource(R.mipmap.menu_restroom);
                break;
            case PinTypeModel.Rickshaw:
                pinImage.setImageResource(R.mipmap.menu_rickshaw);
                break;
            case PinTypeModel.Beer:
                if(pinTag != null && !pinTag.isEmpty()){
                    setBarImage(pinTag, pinImage);
                }else{
                    pinImage.setImageResource(R.mipmap.menu_beer);
                }
                break;
            case PinTypeModel.Broadcast:
                pinImage.setImageResource(R.mipmap.menu_boardcast);
                break;
            case PinTypeModel.Celebrity:
                pinImage.setImageResource(R.mipmap.menu_celebrity);
                break;
            case PinTypeModel.FoodDrink:
                pinImage.setImageResource(R.mipmap.menu_food);
                break;
            case PinTypeModel.GameShowing:
                pinImage.setImageResource(R.mipmap.menu_showing);
                break;
            case PinTypeModel.MedicalCare:
                pinImage.setImageResource(R.mipmap.menu_medical);
                break;
            case PinTypeModel.Meetup:
                pinImage.setImageResource(R.mipmap.menu_meetme);
                break;
            case PinTypeModel.Music:
                pinImage.setImageResource(R.mipmap.menu_music);
                break;
            case PinTypeModel.Note:
                pinImage.setImageResource(R.mipmap.menu_note);
                break;
            case PinTypeModel.Parking:
                pinImage.setImageResource(R.mipmap.menu_parking);
                break;
            case PinTypeModel.Partying:
                pinImage.setImageResource(R.mipmap.menu_partying);
                break;
            case PinTypeModel.Playing:
                pinImage.setImageResource(R.mipmap.menu_playing);
                break;
            case PinTypeModel.Tailgate:
                pinImage.setImageResource(R.mipmap.menu_tailgate);
                break;
            case PinTypeModel.Taxi:
                pinImage.setImageResource(R.mipmap.menu_taxi);
                break;
            case PinTypeModel.Ticket:
                pinImage.setImageResource(R.mipmap.menu_ticket);
                break;
            case PinTypeModel.Treasure:
                pinImage.setImageResource(R.mipmap.menu_treasure);
                break;
            case PinTypeModel.WatchParty:
                pinImage.setImageResource(R.mipmap.menu_watching);
                break;
            case PinTypeModel.Police:
                pinImage.setImageResource(R.mipmap.menu_police);
        }

        return;
    }

       public static void setBarImage(String mapPinTags, ImageView pinImage){
        if (mapPinTags.contains("American Outlaws"))
            pinImage.setImageResource(R.mipmap.ic_beer_american);
        else if (mapPinTags.contains("Bears"))
            pinImage.setImageResource(R.mipmap.ic_beer_bears);
        else if (mapPinTags.contains("Bills"))
            pinImage.setImageResource(R.mipmap.ic_beer_bills);
        else if (mapPinTags.contains("Colts"))
            pinImage.setImageResource(R.mipmap.ic_beer_colts);
        else if (mapPinTags.contains("Jets"))
            pinImage.setImageResource(R.mipmap.ic_beer_jets);
        else if (mapPinTags.contains("Lions"))
            pinImage.setImageResource(R.mipmap.ic_beer_lions);
        else if (mapPinTags.contains("49ers"))
            pinImage.setImageResource(R.mipmap.ic_beer_49ers);
        else if (mapPinTags.contains("Bengals"))
            pinImage.setImageResource(R.mipmap.ic_beer_bengals);
        else if (mapPinTags.contains("Bronocos"))
            pinImage.setImageResource(R.mipmap.ic_beer_bronocos);
        else if (mapPinTags.contains("Browns"))
            pinImage.setImageResource(R.mipmap.ic_beer_browns);
        else if (mapPinTags.contains("Buccaneers"))
            pinImage.setImageResource(R.mipmap.ic_beer_buccaneers);
        else if (mapPinTags.contains("Cardinals"))
            pinImage.setImageResource(R.mipmap.ic_beer_arizona_cardinals);
        else if (mapPinTags.contains("Chargers"))
            pinImage.setImageResource(R.mipmap.ic_beer_chargers);
        else if (mapPinTags.contains("Chiefs"))
            pinImage.setImageResource(R.mipmap.ic_beer_chiefs);
        else if (mapPinTags.contains("Cowboys"))
            pinImage.setImageResource(R.mipmap.ic_beer_cowboys);
        else if (mapPinTags.contains("Dolphins"))
            pinImage.setImageResource(R.mipmap.ic_beer_dolphins);
        else if (mapPinTags.contains("Eagles"))
            pinImage.setImageResource(R.mipmap.ic_beer_eagles);
        else if (mapPinTags.contains("Falcons"))
            pinImage.setImageResource(R.mipmap.ic_beer_falcons);
        else if (mapPinTags.contains("Giants"))
            pinImage.setImageResource(R.mipmap.ic_beer_giants);
        else if (mapPinTags.contains("Jaguars"))
            pinImage.setImageResource(R.mipmap.ic_beer_jaguars);
        else if (mapPinTags.contains("Packers"))
            pinImage.setImageResource(R.mipmap.ic_beer_packers);
        else if (mapPinTags.contains("Panthers"))
            pinImage.setImageResource(R.mipmap.ic_beer_panthers);
        else if (mapPinTags.contains("Patriots"))
            pinImage.setImageResource(R.mipmap.ic_beer_patriots);
        else if (mapPinTags.contains("Raiders"))
            pinImage.setImageResource(R.mipmap.ic_beer_raiders);
        else if (mapPinTags.contains("Rams"))
            pinImage.setImageResource(R.mipmap.ic_beer_rams);
        else if (mapPinTags.contains("Ravens"))
            pinImage.setImageResource(R.mipmap.ic_beer_ravens);
        else if (mapPinTags.contains("Redskin"))
            pinImage.setImageResource(R.mipmap.ic_beer_redskin);
        else if (mapPinTags.contains("Saints"))
            pinImage.setImageResource(R.mipmap.ic_beer_saints);
        else if (mapPinTags.contains("Seahawks"))
            pinImage.setImageResource(R.mipmap.ic_beer_seahawks);
        else if (mapPinTags.contains("Steelers"))
            pinImage.setImageResource(R.mipmap.ic_beer_steelers);
        else if (mapPinTags.contains("Texans"))
            pinImage.setImageResource(R.mipmap.ic_beer_texans);
        else if (mapPinTags.contains("Titans"))
            pinImage.setImageResource(R.mipmap.ic_beer_titans);
        else if (mapPinTags.contains("Vikings"))
            pinImage.setImageResource(R.mipmap.ic_beer_vikings);
        else if (mapPinTags.contains("Arsenal"))
            pinImage.setImageResource(R.mipmap.ic_beer_arsenal);
        else if (mapPinTags.contains("Barcelona"))
            pinImage.setImageResource(R.mipmap.ic_beer_barcelona);
        else if (mapPinTags.contains("Chelsea"))
            pinImage.setImageResource(R.mipmap.ic_beer_chelsea);
        else if (mapPinTags.contains("Liverpool"))
            pinImage.setImageResource(R.mipmap.ic_beer_liverpool);
        else if (mapPinTags.contains("Manchester United"))
            pinImage.setImageResource(R.mipmap.ic_beer_manchester_united);
        else if (mapPinTags.contains("Florida"))
            pinImage.setImageResource(R.mipmap.ic_beer_florida_st);
        else if (mapPinTags.contains("Ohio"))
            pinImage.setImageResource(R.mipmap.ic_beer_ohio_st);
        else if (mapPinTags.contains("Iowa"))
            pinImage.setImageResource(R.mipmap.ic_beer_iowa);
        else if (mapPinTags.contains("LSU"))
            pinImage.setImageResource(R.mipmap.ic_beer_lsu);
        else if (mapPinTags.contains("Texas"))
            pinImage.setImageResource(R.mipmap.ic_beer_texas);
        else if (mapPinTags.contains("USC"))
            pinImage.setImageResource(R.mipmap.ic_beer_usc);
        else if (mapPinTags.contains("Utah"))
            pinImage.setImageResource(R.mipmap.ic_beer_utah);
        else if (mapPinTags.contains("Air Force"))
            pinImage.setImageResource(R.mipmap.ic_beer_air_force);
        else if (mapPinTags.contains("Alabama"))
            pinImage.setImageResource(R.mipmap.ic_beer_alabama);
        else if (mapPinTags.contains("Arizona"))
            pinImage.setImageResource(R.mipmap.ic_beer_arizona);
        else if (mapPinTags.contains("Auburn"))
            pinImage.setImageResource(R.mipmap.ic_beer_auburn);
        else if (mapPinTags.contains("Georgia Tech"))
            pinImage.setImageResource(R.mipmap.ic_beer_georgia_tech);
        else if (mapPinTags.contains("Georgia"))
            pinImage.setImageResource(R.mipmap.ic_beer_georgia);
        else if (mapPinTags.contains("Kentucky"))
            pinImage.setImageResource(R.mipmap.ic_beer_kentucky);
        else if (mapPinTags.contains("Louisville"))
            pinImage.setImageResource(R.mipmap.ic_beer_louisville);
        else if (mapPinTags.contains("Michigan State"))
            pinImage.setImageResource(R.mipmap.ic_beer_michigan_st);
        else if (mapPinTags.contains("Michigan"))
            pinImage.setImageResource(R.mipmap.ic_beer_michigan);
        else if (mapPinTags.contains("Missouri"))
            pinImage.setImageResource(R.mipmap.ic_beer_missouri);
        else if (mapPinTags.contains("Nebraska"))
            pinImage.setImageResource(R.mipmap.ic_beer_nebraska);
        else if (mapPinTags.contains("North Carolina"))
            pinImage.setImageResource(R.mipmap.ic_beer_north_carolina);
        else if (mapPinTags.contains("Notre Dame"))
            pinImage.setImageResource(R.mipmap.ic_beer_notre_dame);
        else if (mapPinTags.contains("Oklahoma"))
            pinImage.setImageResource(R.mipmap.ic_beer_oklahoma);
        else if (mapPinTags.contains("Oregon"))
            pinImage.setImageResource(R.mipmap.ic_beer_oregon);
        else if (mapPinTags.contains("Pittsburgh"))
            pinImage.setImageResource(R.mipmap.ic_beer_pittsburgh);
        else if (mapPinTags.contains("Purdue"))
            pinImage.setImageResource(R.mipmap.ic_beer_purdue);
        else if (mapPinTags.contains("Syracuse"))
            pinImage.setImageResource(R.mipmap.ic_beer_syracuse);
        else if (mapPinTags.contains("Virginia Tech"))
            pinImage.setImageResource(R.mipmap.ic_beer_virginia_tech);
        else
            pinImage.setImageResource(R.mipmap.ic_beer_defaultbeer);
    }


    public static void setBeerIconByTag(String mapPinTags, MarkerOptions markerOptions){
        if (mapPinTags.contains("American Outlaws"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_american));
        else if (mapPinTags.contains("Bears"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_bears));
        else if (mapPinTags.contains("Bills"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_bills));
        else if (mapPinTags.contains("Colts"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_colts));
        else if (mapPinTags.contains("Jets"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_jets));
        else if (mapPinTags.contains("Lions"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_lions));
        else if (mapPinTags.contains("49ers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_49ers));
        else if (mapPinTags.contains("Bengals"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_bengals));
        else if (mapPinTags.contains("Bronocos"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_bronocos));
        else if (mapPinTags.contains("Browns"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_browns));
        else if (mapPinTags.contains("Buccaneers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_buccaneers));
        else if (mapPinTags.contains("Cardinals"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_arizona_cardinals));
        else if (mapPinTags.contains("Chargers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_chargers));
        else if (mapPinTags.contains("Chiefs"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_chiefs));
        else if (mapPinTags.contains("Cowboys"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_cowboys));
        else if (mapPinTags.contains("Dolphins"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_dolphins));
        else if (mapPinTags.contains("Eagles"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_eagles));
        else if (mapPinTags.contains("Falcons"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_falcons));
        else if (mapPinTags.contains("Giants"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_giants));
        else if (mapPinTags.contains("Jaguars"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_jaguars));
        else if (mapPinTags.contains("Packers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_packers));
        else if (mapPinTags.contains("Panthers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_panthers));
        else if (mapPinTags.contains("Patriots"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_patriots));
        else if (mapPinTags.contains("Raiders"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_raiders));
        else if (mapPinTags.contains("Rams"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_rams));
        else if (mapPinTags.contains("Ravens"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_ravens));
        else if (mapPinTags.contains("Redskin"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_redskin));
        else if (mapPinTags.contains("Saints"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_saints));
        else if (mapPinTags.contains("Seahawks"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_seahawks));
        else if (mapPinTags.contains("Steelers"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_steelers));
        else if (mapPinTags.contains("Texans"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_texans));
        else if (mapPinTags.contains("Titans"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_titans));
        else if (mapPinTags.contains("Vikings"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_vikings));
        else if (mapPinTags.contains("Arsenal"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_arsenal));
        else if (mapPinTags.contains("Barcelona"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_barcelona));
        else if (mapPinTags.contains("Chelsea"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_chelsea));
        else if (mapPinTags.contains("Liverpool"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_liverpool));
        else if (mapPinTags.contains("Manchester United"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_manchester_united));
        else if (mapPinTags.contains("Florida"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_florida_st));
        else if (mapPinTags.contains("Ohio"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_ohio_st));
        else if (mapPinTags.contains("Iowa"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_iowa));
        else if (mapPinTags.contains("LSU"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_lsu));
        else if (mapPinTags.contains("Texas"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_texas));
        else if (mapPinTags.contains("USC"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_usc));
        else if (mapPinTags.contains("Utah"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_utah));
        else if (mapPinTags.contains("Air Force"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_air_force));
        else if (mapPinTags.contains("Alabama"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_alabama));
        else if (mapPinTags.contains("Arizona"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_arizona));
        else if (mapPinTags.contains("Auburn"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_auburn));
        else if (mapPinTags.contains("Georgia Tech"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_georgia_tech));
        else if (mapPinTags.contains("Georgia"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_georgia));
        else if (mapPinTags.contains("Kentucky"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_kentucky));
        else if (mapPinTags.contains("Louisville"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_louisville));
        else if (mapPinTags.contains("Michigan State"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_michigan_st));
        else if (mapPinTags.contains("Michigan"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_michigan));
        else if (mapPinTags.contains("Missouri"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_missouri));
        else if (mapPinTags.contains("Nebraska"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_nebraska));
        else if (mapPinTags.contains("North Carolina"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_north_carolina));
        else if (mapPinTags.contains("Notre Dame"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_notre_dame));
        else if (mapPinTags.contains("Oklahoma"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_oklahoma));
        else if (mapPinTags.contains("Oregon"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_oregon));
        else if (mapPinTags.contains("Pittsburgh"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_pittsburgh));
        else if (mapPinTags.contains("Purdue"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_purdue));
        else if (mapPinTags.contains("Syracuse"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_syracuse));
        else if (mapPinTags.contains("Virginia Tech"))
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_virginia_tech));
        else
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer_defaultbeer));
    }

    public static String stickImageString[] = {
            "stick_boy_smile",
            "stick_boy_sad",
            "stick_boy_angry",
            "stick_boy_wasntme",
            "stick_boy_crying",
            "stick_boy_sweat",
            "stick_vampire_sweat",
            "stick_monster_sad",
            "stick_monster_angry",
            "stick_monster_wasntme",
            "stick_zombie_quite",
            "stick_zombie_sad",
            "stick_boy_scared",
            "stick_boy_wink",
            "stick_girl_smile",
            "stick_girl_sad",
            "stick_girl_angry",
            "stick_girl_wasntme",
            "stick_zombie_smile",
            "stick_burger",
            "stick_hotdog",
            "stick_icecream",
            "stick_lolipop",
            "stick_pizza",
            "stick_girl_crying",
            "stick_girl_sweat",
            "stick_girl_scared",
            "stick_girl_wink",
            "stick_vampire_smile",
            "stick_vampire_angry",
            "stick_rocket",
            "stick_shark",
            "stick_viking",
            "stick_ghost",
            "stick_mummy",
            "stick_boy_smile" };

    public static Drawable getStickImageFromResource(String filename, Context context){
        Drawable drawable;
        switch (filename){
            case "stick_boy_smile":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_smile);
                break;
            case "stick_boy_sad":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_sad);
                break;
            case "stick_boy_angry":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_angry);
                break;
            case "stick_boy_wasntme":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_wasntme);
                break;
            case "stick_boy_crying":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_crying);
                break;
            case "stick_boy_sweat":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_sweat);
                break;
            case "stick_vampire_sweat":
                drawable = context.getResources().getDrawable(R.mipmap.stick_vampire_sweat);
                break;
            case "stick_monster_sad":
                drawable = context.getResources().getDrawable(R.mipmap.stick_monster_sad);
                break;
            case "stick_monster_angry":
                drawable = context.getResources().getDrawable(R.mipmap.stick_monster_angry);
                break;
            case "stick_monster_wasntme":
                drawable = context.getResources().getDrawable(R.mipmap.stick_monster_wasntme);
                break;
            case "stick_zombie_quite":
                drawable = context.getResources().getDrawable(R.mipmap.stick_zombie_quite);
                break;
            case "stick_zombie_sad":
                drawable = context.getResources().getDrawable(R.mipmap.stick_zombie_sad);
                break;
            case "stick_boy_scared":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_scared);
                break;
            case "stick_boy_wink":
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_wink);
                break;
            case "stick_girl_smile":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_smile);
                break;
            case "stick_girl_sad":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_sad);
                break;
            case "stick_girl_angry":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_angry);
                break;
            case "stick_girl_wasntme":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_wasntme);
                break;
            case "stick_zombie_smile":
                drawable = context.getResources().getDrawable(R.mipmap.stick_zombie_smile);
                break;
            case "stick_burger":
                drawable = context.getResources().getDrawable(R.mipmap.stick_burger);
                break;
            case "stick_hotdog":
                drawable = context.getResources().getDrawable(R.mipmap.stick_hotdog);
                break;
            case "stick_icecream":
                drawable = context.getResources().getDrawable(R.mipmap.stick_icecream);
                break;
            case "stick_lolipop":
                drawable = context.getResources().getDrawable(R.mipmap.stick_lolipop);
                break;
            case "stick_pizza":
                drawable = context.getResources().getDrawable(R.mipmap.stick_pizza);
                break;
            case "stick_girl_crying":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_crying);
                break;
            case "stick_girl_sweat":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_sweat);
                break;
            case "stick_girl_scared":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_scared);
                break;
            case "stick_girl_wink":
                drawable = context.getResources().getDrawable(R.mipmap.stick_girl_wink);
                break;
            case "stick_vampire_smile":
                drawable = context.getResources().getDrawable(R.mipmap.stick_vampire_smile);
                break;
            case "stick_vampire_angry":
                drawable = context.getResources().getDrawable(R.mipmap.stick_vampire_angry);
                break;
            case "stick_rocket":
                drawable = context.getResources().getDrawable(R.mipmap.stick_rocket);
                break;
            case "stick_shark":
                drawable = context.getResources().getDrawable(R.mipmap.stick_shark);
                break;
            case "stick_viking":
                drawable = context.getResources().getDrawable(R.mipmap.stick_viking);
                break;
            case "stick_ghost":
                drawable = context.getResources().getDrawable(R.mipmap.stick_ghost);
                break;
            case "stick_mummy":
                drawable = context.getResources().getDrawable(R.mipmap.stick_mummy);
                break;
            default:
                drawable = context.getResources().getDrawable(R.mipmap.stick_boy_angry);
                break;
        }

        return  drawable;
    }

    public static void setStickImage(String stickType, ImageView pinImage) {
        if (stickType.contains("boy_smile"))
            pinImage.setImageResource(R.mipmap.stick_boy_smile);
        else if (stickType.contains("boy_sad"))
            pinImage.setImageResource(R.mipmap.stick_boy_sad);
        else if (stickType.contains("boy_angry"))
            pinImage.setImageResource(R.mipmap.stick_boy_angry);
        else if (stickType.contains("boy_wasntme"))
            pinImage.setImageResource(R.mipmap.stick_boy_wasntme);
        else if (stickType.contains("boy_crying"))
            pinImage.setImageResource(R.mipmap.stick_boy_crying);
        else if (stickType.contains("boy_sweat"))
            pinImage.setImageResource(R.mipmap.stick_boy_sweat);
        else if (stickType.contains("vampire_sweat"))
            pinImage.setImageResource(R.mipmap.stick_vampire_sweat);
        else if (stickType.contains("monster_sad"))
            pinImage.setImageResource(R.mipmap.stick_monster_sad);
        else if (stickType.contains("monster_angry"))
            pinImage.setImageResource(R.mipmap.stick_monster_angry);
        else if (stickType.contains("monster_wasntme"))
            pinImage.setImageResource(R.mipmap.stick_monster_wasntme);
        else if (stickType.contains("zombie_quite"))
            pinImage.setImageResource(R.mipmap.stick_zombie_quite);
        else if (stickType.contains("zombie_sad"))
            pinImage.setImageResource(R.mipmap.stick_zombie_sad);
        else if (stickType.contains("boy_scared"))
            pinImage.setImageResource(R.mipmap.stick_boy_scared);
        else if (stickType.contains("boy_wink"))
            pinImage.setImageResource(R.mipmap.stick_boy_wink);
        else if (stickType.contains("girl_smile"))
            pinImage.setImageResource(R.mipmap.stick_girl_smile);
        else if (stickType.contains("girl_sad"))
            pinImage.setImageResource(R.mipmap.stick_girl_sad);
        else if (stickType.contains("girl_angry"))
            pinImage.setImageResource(R.mipmap.stick_girl_angry);
        else if (stickType.contains("girl_wasntme"))
            pinImage.setImageResource(R.mipmap.stick_girl_wasntme);
        else if (stickType.contains("zombie_smile"))
            pinImage.setImageResource(R.mipmap.stick_zombie_smile);
        else if (stickType.contains("burger"))
            pinImage.setImageResource(R.mipmap.stick_burger);
        else if (stickType.contains("hotdog"))
            pinImage.setImageResource(R.mipmap.stick_hotdog);
        else if (stickType.contains("icecream"))
            pinImage.setImageResource(R.mipmap.stick_icecream);
        else if (stickType.contains("lolipop"))
            pinImage.setImageResource(R.mipmap.stick_lolipop);
        else if (stickType.contains("pizza"))
            pinImage.setImageResource(R.mipmap.stick_pizza);
        else if (stickType.contains("girl_crying"))
            pinImage.setImageResource(R.mipmap.stick_girl_crying);
        else if (stickType.contains("girl_sweat"))
            pinImage.setImageResource(R.mipmap.stick_girl_sweat);
        else if (stickType.contains("girl_scared"))
            pinImage.setImageResource(R.mipmap.stick_girl_scared);
        else if (stickType.contains("girl_wink"))
            pinImage.setImageResource(R.mipmap.stick_girl_wink);
        else if (stickType.contains("vampire_smile"))
            pinImage.setImageResource(R.mipmap.stick_vampire_smile);
        else if (stickType.contains("vampire_angry"))
            pinImage.setImageResource(R.mipmap.stick_vampire_angry);
        else if (stickType.contains("Beer"))
            pinImage.setImageResource(R.mipmap.menu_beer);
        else if (stickType.contains("rocket"))
            pinImage.setImageResource(R.mipmap.stick_rocket);
        else if (stickType.contains("shark"))
            pinImage.setImageResource(R.mipmap.stick_shark);
        else if (stickType.contains("viking"))
            pinImage.setImageResource(R.mipmap.stick_viking);
        else if (stickType.contains("ghost"))
            pinImage.setImageResource(R.mipmap.stick_ghost);
        else if (stickType.contains("mummy"))
            pinImage.setImageResource(R.mipmap.stick_mummy);
        else
            pinImage.setImageResource(R.mipmap.stick_boy_smile);
    }

    public static String ConvertTimeToAgo(String origin) {
        Date curTime = new Date();
        long curSecond = curTime.getTime() / 1000; //second

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date originDate = dateFormat.parse(origin);
            long originSecond = originDate.getTime() / 1000; //second

            long deltaSeconds  = curSecond - originSecond;
            long deltaMinutes = deltaSeconds / 60;

            if(deltaSeconds < 5)
            {
                return "Just now";
            }
            else if(deltaSeconds < 60)
            {
                return deltaSeconds + " seconds ago";
            }
            else if(deltaSeconds < 120)
            {
                return "A minute ago";
            }
            else if (deltaMinutes < 60)
            {
                return deltaMinutes + " minutes ago";
            }
            else if (deltaMinutes < 120)
            {
                return "An hour ago";
            }
            else if (deltaMinutes < (24 * 60))
            {
                int hour = (int)(deltaMinutes/60);
                return hour + " hours ago";
            }
            else if (deltaMinutes < (24 * 60 * 2))
            {
                return "Yesterday";
            }
            else if (deltaMinutes < (24 * 60 * 7))
            {
                int day = (int)(deltaMinutes/(60 * 24));
                return day + " days ago";
            }
            else if (deltaMinutes < (24 * 60 * 14))
            {
                return "Last week";
            }
            else if (deltaMinutes < (24 * 60 * 31))
            {
                int week = (int)(deltaMinutes/(60 * 24 * 7));
                return week + " weeks ago";
            }
            else if (deltaMinutes < (24 * 60 * 61))
            {
                return "Last month";
            }
            else if (deltaMinutes < (24 * 60 * 365.25))
            {
                int month = (int)(deltaMinutes/(60 * 24 * 30));
                return month + " months ago";
            }
            else if (deltaMinutes < (24 * 60 * 731))
            {
                return "Last year";
            }else{
                int year = (int)(deltaMinutes/(60 * 24 * 365));
                return year + " years ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ChatModel CreateChatModelFromJson(JsonObject jsonObject){
        ChatModel model = new ChatModel();

        if(jsonObject.get("local_chat_id") != null)
            model.setLocalChatId(jsonObject.get("local_chat_id").getAsString());
        else
            model.setLocalChatId("");

        if(jsonObject.get("user_id") != null)
            model.setUserId(jsonObject.get("user_id").getAsString());
        else
            model.setUserId("");

        if(jsonObject.get("chat_message") != null)
            model.setChatMessage(jsonObject.get("chat_message").getAsString());
        else
            model.setChatMessage("");

        if(jsonObject.get("chat_photo") != null)
            model.setChatPhoto(jsonObject.get("chat_photo").getAsString());
        else
            model.setChatPhoto("");

        if(jsonObject.get("chat_video_link") != null)
            model.setChatVideoLink(jsonObject.get("chat_video_link").getAsString());
        else
            model.setChatVideoLink("");

        if(jsonObject.get("chat_latitude") != null)
            model.setChatLatitude(jsonObject.get("chat_latitude").getAsString());
        else
            model.setChatLatitude("");

        if(jsonObject.get("chat_longitude") != null)
            model.setChatLongitude(jsonObject.get("chat_longitude").getAsString());
        else
            model.setChatLongitude("");

        if(jsonObject.get("chat_like_count") != null)
            model.setChatLikeCount(jsonObject.get("chat_like_count").getAsString());
        else
            model.setChatLikeCount("");

        if(jsonObject.get("chat_date_created") != null)
            model.setChatDateCreated(jsonObject.get("chat_date_created").getAsString());
        else
            model.setChatDateCreated("");

        if(jsonObject.get("fullname") != null)
            model.setFullname(jsonObject.get("fullname").getAsString());
        else
            model.setFullname("");

        if(jsonObject.get("current_latitude") != null)
            model.setCurrentLatitude(jsonObject.get("current_latitude").getAsString());
        else
            model.setCurrentLatitude("");

        if(jsonObject.get("current_longitude") != null)
            model.setCurrentLongitude(jsonObject.get("current_longitude").getAsString());
        else
            model.setCurrentLongitude("");

        if(jsonObject.get("profile_avatar") != null)
            model.setProfileAvatar(jsonObject.get("profile_avatar").getAsString());
        else
            model.setProfileAvatar("");

        if(jsonObject.get("liked") != null)
            model.setLiked(jsonObject.get("liked").getAsBoolean());
        else
            model.setLiked(false);

        if(jsonObject.get("distance") != null)
            model.setDistance(jsonObject.get("distance").getAsString());
        else
            model.setDistance("");

        return model;
    };


    public static PinDetailModel CreatePinDetailModelFromJson(JsonObject jsonObject){
        PinDetailModel model = new PinDetailModel();

        if(jsonObject.get("pin_chat_id") != null)
            model.setPinChatId(jsonObject.get("pin_chat_id").getAsString());
        else
            model.setPinChatId("");

        if(jsonObject.get("user_id") != null)
            model.setUserId(jsonObject.get("user_id").getAsString());
        else
            model.setUserId("");

        if(jsonObject.get("pin_chat_message") != null)
            model.setPinChatMessage(jsonObject.get("pin_chat_message").getAsString());
        else
            model.setPinChatMessage("");

        if(jsonObject.get("pin_chat_photo") != null)
            model.setPinChatPhoto(jsonObject.get("pin_chat_photo").getAsString());
        else
            model.setPinChatPhoto("");

        if(jsonObject.get("pin_chat_video_link") != null)
            model.setPinChatVideoLink(jsonObject.get("pin_chat_video_link").getAsString());
        else
            model.setPinChatVideoLink("");

        if(jsonObject.get("pin_chat_latitude") != null)
            model.setPinChatLatitude(jsonObject.get("pin_chat_latitude").getAsString());
        else
            model.setPinChatLatitude("");

        if(jsonObject.get("pin_chat_longitude") != null)
            model.setPinChatLongitude(jsonObject.get("pin_chat_longitude").getAsString());
        else
            model.setPinChatLongitude("");

        if(jsonObject.get("pin_chat_like_count") != null)
            model.setPinChatLikeCount(jsonObject.get("pin_chat_like_count").getAsString());
        else
            model.setPinChatLikeCount("");

        if(jsonObject.get("pin_chat_date_created") != null)
            model.setPinChatDateCreated(jsonObject.get("pin_chat_date_created").getAsString());
        else
            model.setPinChatDateCreated("");

        if(jsonObject.get("fullname") != null)
            model.setFullname(jsonObject.get("fullname").getAsString());
        else
            model.setFullname("");

        if(jsonObject.get("current_latitude") != null)
            model.setCurrentLatitude(jsonObject.get("current_latitude").getAsString());
        else
            model.setCurrentLatitude("");

        if(jsonObject.get("current_longitude") != null)
            model.setCurrentLongitude(jsonObject.get("current_longitude").getAsString());
        else
            model.setCurrentLongitude("");

        if(jsonObject.get("liked") != null)
            model.setLiked(jsonObject.get("liked").getAsBoolean());
        else
            model.setLiked(false);

        if(jsonObject.get("profile_avatar") != null)
            model.setProfileAvatar(jsonObject.get("profile_avatar").getAsString());
        else
            model.setProfileAvatar("");

        if(jsonObject.get("distance") != null)
            model.setDistance(jsonObject.get("distance").getAsString());
        else
            model.setDistance("");

        return model;
    };


    public static GroupChatModel CreateGroupChatModelFromJson(JsonObject jsonObject){

        GroupChatModel model = new GroupChatModel();

        if(jsonObject.get("group_chat_id") != null)
            model.setGroupChatId(jsonObject.get("group_chat_id").getAsString());
        else
            model.setGroupChatId("");

        if(jsonObject.get("group_id") != null)
            model.setGroupId(jsonObject.get("group_id").getAsString());
        else
            model.setGroupId("");

        if(jsonObject.get("user_id") != null)
            model.setUserId(jsonObject.get("user_id").getAsString());
        else
            model.setUserId("");

        if(jsonObject.get("chat_message") != null)
            model.setChatMessage(jsonObject.get("chat_message").getAsString());
        else
            model.setChatMessage("");

        if(jsonObject.get("chat_photo") != null)
            model.setChatPhoto(jsonObject.get("chat_photo").getAsString());
        else
            model.setChatPhoto("");

        if(jsonObject.get("chat_video_link") != null)
            model.setChatVideoLink(jsonObject.get("chat_video_link").getAsString());
        else
            model.setChatVideoLink("");

        if(jsonObject.get("chat_latitude") != null)
            model.setChatLatitude(jsonObject.get("chat_latitude").getAsString());
        else
            model.setChatLatitude("");

        if(jsonObject.get("chat_longitude") != null)
            model.setChatLongitude(jsonObject.get("chat_longitude").getAsString());
        else
            model.setChatLongitude("");

        if(jsonObject.get("chat_like_count") != null)
            model.setChatLikeCount(jsonObject.get("chat_like_count").getAsString());
        else
            model.setChatLikeCount("");

        if(jsonObject.get("chat_date_created") != null)
            model.setChatDateCreated(jsonObject.get("chat_date_created").getAsString());
        else
            model.setChatDateCreated("");

        if(jsonObject.get("fullname") != null)
            model.setFullname(jsonObject.get("fullname").getAsString());
        else
            model.setFullname("");

        if(jsonObject.get("current_latitude") != null)
            model.setCurrentLatitude(jsonObject.get("current_latitude").getAsString());
        else
            model.setCurrentLatitude("");

        if(jsonObject.get("current_longitude") != null)
            model.setCurrentLongitude(jsonObject.get("current_longitude").getAsString());
        else
            model.setCurrentLongitude("");

        if(jsonObject.get("profile_avatar") != null)
            model.setProfileAvatar(jsonObject.get("profile_avatar").getAsString());
        else
            model.setProfileAvatar("");

        if(jsonObject.get("distance") != null)
            model.setDistance(jsonObject.get("distance").getAsString());
        else
            model.setDistance("");

        return model;
    };
}
