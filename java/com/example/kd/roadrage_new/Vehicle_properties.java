package com.example.kd.roadrage_new;

/*
 *
 *Human Computer Interaction - CS6326.001
 *under prof. John Cole
 *Project title: Roadrage
 *
 *Team:
 *Keertan Dakarapu (kxd160830)
 *Dhawal Parmar (ddp160330)
 *
*/
/*
    Module developed by Keertan Dakarapu - kxd160830
*/
public class Vehicle_properties {

    public float width;
    public float height;

    // Sets the properties of the selected car
    public Vehicle_properties(int vehicle_type, float screenX)
    {
        this.width = 200;
        this.height = 300;

        switch (vehicle_type)
        {
            //update the values according to the screenX

            case 0: //car 1
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)284/(float)150)*this.width;
                //this.width = 150;
                //this.height = 350;
                break;
            case 1: //car 2
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)308/(float)150)*this.width;
                break;
            case 2: //car 2
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)352/(float)150)*this.width;
                break;
            case 3: //car 2
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)284/(float)150)*this.width;
                break;
            case 4: //car 2
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)362/(float)150)*this.width;
                break;
            case 5: //car 2
                this.width = ((float)150/(float)1080)*screenX;
                this.height = ((float)362/(float)150)*this.width;
                break;
            case 11: //truck1
                this.width = ((float)180/(float)1080)*screenX;
                this.height = ((float)448/(float)180)*this.width;
                break;
            case 12: //truck2
                this.width = 200;
                this.height = 500;
                break;
            case 13: //police car
                this.width = 200;
                this.height = 390;
                break;
            default: //truck1
                this.width = 200;
                this.height = 500;
                break;

        }

    }

}
