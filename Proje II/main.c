#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <string.h>

int para[5];
char hizmetAdi1[10];
char hizmetAdi2[10];
char hizmetAdi3[10];
char hizmetAdi4[10];
int hizmetMiktar[4];
int hizmetFiyat[4];
int yatirilan = 0;
int hizmetler[4];

int main()
{
    dosyaOku();
    return 0;
}

void dosyaOku()
{
    int i, j = 0;
    int line = 1;
    char inputtxt[100];

    FILE *dosya= fopen("hizmetler.txt","r");
    if (dosya==NULL)
    {
        printf("Dosya okunamadi!");
    }

    while (1)
    {
        if (fgets(inputtxt,150, dosya) == NULL)
            break;

        const char *ayrac=" ,TL";
        char *kelime = strtok(inputtxt,ayrac);
        if(line == 1)
        {
            for(i=0; i<5; i++)
            {
                if(kelime != NULL)
                {
                    printf("%s\n", kelime);
                    para[i] = atoi(kelime);
                }
                kelime = strtok(NULL, ayrac);
            }
            line = 0;
        }
        else
        {
            for(i=0; i<4; i++)
            {
                if(kelime != NULL)
                {
                    if(i == 1)
                    {
                        if(j==0)
                            sprintf(hizmetAdi1, kelime);
                        else if(j==1)
                            sprintf(hizmetAdi2, kelime);
                        else if(j==2)
                            sprintf(hizmetAdi3, kelime);
                        else if(j==3)
                            sprintf(hizmetAdi4, kelime);
                    }
                    else if(i == 2)
                    {
                        hizmetMiktar[j] = atoi(kelime);
                    }
                    else if(i == 3)
                    {
                        hizmetFiyat[j] = atoi(kelime);
                    }
                }
                kelime = strtok(NULL, ayrac);
            }
            j++;
        }
    }

    fclose(dosya);

    paraYukle();
}

void paraYukle()
{
    char buton;
    int i;

    while(buton != 'X')
    {
        printf("\n5 TL (A)\n10 TL (B)\n20 TL (C)\n50 TL (D)\n100 TL (E)\nCikis (X)\nPara girisi yapiniz: ");
        scanf(" %c",&buton);
        switch(buton)
        {
        case 'A':
            yatirilan += 5;
            para[0] += 1;
            break;
        case 'B':
            yatirilan += 10;
            para[1] += 1;
            break;
        case 'C':
            yatirilan += 20;
            para[2] += 1;
            break;
        case 'D':
            yatirilan += 50;
            para[3] += 1;
            break;
        case 'E':
            yatirilan += 100;
            para[4] += 1;
            break;
        default:
            break;
        }
    }
    printf("\n%d TL yuklediniz.\n",yatirilan);

    for(i=0; i<4; i++)
    {
        hizmetler[i] = 0;
    }

    hizmetSec();
}

void hizmetSec()
{
    char buton;
    int i, harcanan = 0;

    while(buton != 'X')
    {
        printf("\nKopukleme (A)\nYikama (B)\nKurulama (C)\nCilalama (D)\nReset (R)\nCikis (X)\nHizmet seciniz: ");
        scanf(" %c",&buton);
        switch(buton)
        {
        case 'A':
            if(hizmetMiktar[0] != 0 && harcanan+hizmetFiyat[0]<=yatirilan)
            {
                hizmetler[0] = hizmetler[0] + 1;
                harcanan += hizmetFiyat[0];
            }
            break;
        case 'B':
            if(hizmetMiktar[1] != 0 && harcanan+hizmetFiyat[1]<=yatirilan)
            {
                hizmetler[1] = hizmetler[1] + 1;
                harcanan += hizmetFiyat[1];
            }
            break;
        case 'C':
            if(hizmetMiktar[2] != 0 && harcanan+hizmetFiyat[2]<=yatirilan)
            {
                hizmetler[2] = hizmetler[2] + 1;
                harcanan += hizmetFiyat[2];
            }
            break;
        case 'D':
            if(hizmetMiktar[3] != 0 && harcanan+hizmetFiyat[3]<=yatirilan)
            {
                hizmetler[3] = hizmetler[3] + 1;
                harcanan += hizmetFiyat[3];
            }
            break;
        case 'R':
            for(i=0; i<4; i++)
            {
                hizmetler[i] = 0;
            }
            harcanan = 0;
            break;
        default:
            break;
        }
    }

    printf("\nSecilen hizmetler:\n\n");
    printf("%s: %d\n", hizmetAdi1, hizmetler[0]);
    printf("%s: %d\n", hizmetAdi2, hizmetler[1]);
    printf("%s: %d\n", hizmetAdi3, hizmetler[2]);
    printf("%s: %d\n", hizmetAdi4, hizmetler[3]);

    srand(time(NULL));
    int random = 1 + rand()%4;

    if(random == 2){
        paraSikisma();
        system("color 04");
	}else{
        paraUstu();
        system("color 02");
	}
}

void paraSikisma()
{
    int iade = yatirilan, i;

    printf("\nParaniz iade ediliyor.\n");

    if(iade/100 != 0 && para[4]>=iade/100)
    {
        printf("%d adet 100 TL\n",iade/100);
        para[4] = para[4] - (iade/100);
        iade -= (iade/100)*100;
    }
    if(iade/50 != 0 && para[3]>=iade/50)
    {
        printf("%d adet 50 TL\n",iade/50);
        para[3] = para[3] - (iade/50);
        iade -= (iade/50)*50;
    }
    if(iade/20 != 0 && para[2]>=iade/20)
    {
        printf("%d adet 20 TL\n",iade/20);
        para[2] = para[2] - (iade/20);
        iade -= (iade/20)*20;
    }
    if(iade/10 != 0 && para[1]>=iade/10)
    {
        printf("%d adet 10 TL\n",iade/10);
        para[1] = para[1] - (iade/10);
        iade -= (iade/10)*10;
    }
    if(iade/5 != 0 && para[0]>=iade/5)
    {
        printf("%d adet 5 TL\n",iade/5);
        para[0] = para[0] - (iade/5);
        iade -= (iade/5)*5;
    }

    kasaGuncelle();
}

void paraUstu()
{
    int harcanacak = 0, kalan, i;

    for(i=0; i<4; i++)
    {
        harcanacak += hizmetler[i]*hizmetFiyat[i];
        hizmetMiktar[i] -= hizmetler[i];
    }

    kalan = yatirilan - harcanacak;
    printf("\nPara Ustu: %d\n",kalan);

    if(kalan/100 != 0 && para[4]>=kalan/100)
    {
        printf("%d adet 100 TL\n",kalan/100);
        para[4] = para[4] - (kalan/100);
        kalan -= (kalan/100)*100;
    }
    if(kalan/50 != 0 && para[3]>=kalan/50)
    {
        printf("%d adet 50 TL\n",kalan/50);
        para[3] = para[3] - (kalan/50);
        kalan -= (kalan/50)*50;
    }
    if(kalan/20 != 0 && para[2]>=kalan/20)
    {
        printf("%d adet 20 TL\n",kalan/20);
        para[2] = para[2] - (kalan/20);
        kalan -= (kalan/20)*20;
    }
    if(kalan/10 != 0 && para[1]>=kalan/10)
    {
        printf("%d adet 10 TL\n",kalan/10);
        para[1] = para[1] - (kalan/10);
        kalan -= (kalan/10)*10;
    }
    if(kalan/5 != 0 && para[0]>=kalan/5)
    {
        printf("%d adet 5 TL\n",kalan/5);
        para[0] = para[0] - (kalan/5);
        kalan -= (kalan/5)*5;
    }

    kasaGuncelle();
}

void kasaGuncelle()
{
    int i, j;
    char outputtxt[500];
    char txt[500];

    FILE *dosya= fopen("hizmetler.txt","w+");
    if (dosya==NULL)
    {
        printf("Dosya okunamadi!");
    }

    strcpy(outputtxt, "");

    for(i=0; i<5; i++)
    {
        sprintf(txt, "%d", para[i]);
        strcat(outputtxt, txt);
        if(i!=4)
            strcat(outputtxt, ",");
    }
    strcat(outputtxt, "\n");

    for(i=0; i<4; i++)
    {
        sprintf(txt, "%d", (i+1));
        strcat(outputtxt, txt);
        strcat(outputtxt, ",");
        switch(i)
        {
        case 0:
            strcat(outputtxt, hizmetAdi1);
            break;
        case 1:
            strcat(outputtxt, hizmetAdi2);
            break;
        case 2:
            strcat(outputtxt, hizmetAdi3);
            break;
        case 3:
            strcat(outputtxt, hizmetAdi4);
            break;
        default:
            break;
        }

        strcat(outputtxt, ",");
        sprintf(txt, "%d", hizmetMiktar[i]);
        strcat(outputtxt, txt);
        strcat(outputtxt, ",");
        sprintf(txt, "%d", hizmetFiyat[i]);
        strcat(outputtxt, txt);
        strcat(outputtxt, " TL\n");
    }

    fputs(outputtxt, dosya);

    fclose(dosya);
}
