#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> // open/close
#include <fcntl.h> // O_RDWR
#include <sys/ioctl.h> // ioctl
#include <sys/mman.h> // mmap PROT_
#include <linux/fb.h>
#define FBDEV_FILE "/dev/fb0"
int main( int argc, char **argv)
{
    int screen_width;
    int screen_height;
    int bits_per_pixel;
    int line_length;
    
    int fb_fd;
    struct fb_var_screeninfo fbvar;
    struct fb_fix_screeninfo fbfix;
    unsigned char *fb_mapped;
    int mem_size;
    unsigned short *ptr;
    
    int coor_y;
    int coor_x;
    int i;
    
    printf("Frame buffer Application - ColorBar\n");
    
    if(access(FBDEV_FILE, F_OK))
    {
        printf("%s: access error\n", FBDEV_FILE);
        exit(1);
    }
    
    if((fb_fd = open( FBDEV_FILE, O_RDWR)) <0)
    {
        printf("%s: open error\n", FBDEV_FILE);
        exit(1);
    }
    
    if( ioctl(fb_fd, FBIOGET_VSCREENINFO, &fbvar))
    {
        printf("%s: ioctl error - FBIOGET_VSCREENINFO \n", FBDEV_FILE);
        exit(1);
    }
    
    if( ioctl(fb_fd, FBIOGET_FSCREENINFO, &fbfix))
    {
        printf("%s: ioctl error - FBIOGET_FSCREENINFO \n", FBDEV_FILE);
        exit(1);
    }
    screen_width = fbvar.xres; // 스크린의 픽셀 폭
    screen_height = fbvar.yres; // 스크린의 픽셀 높이
    bits_per_pixel = fbvar.bits_per_pixel; // 픽셀 당 비트 개수
    line_length = fbfix.line_length; // 한개 라인 당 바이트 개수
    
    printf("screen_width : %d\n", screen_width);
    printf("screen_height : %d\n", screen_height);
    printf("bits_per_pixel : %d\n", bits_per_pixel);
    printf("line_length : %d\n", line_length);
    
    mem_size = screen_width * screen_height*2;

    fb_mapped = (unsigned char *)mmap(0, mem_size, PROT_READ|PROT_WRITE, MAP_SHARED, fb_fd, 0);
    
    // fb clear - black(0x0)
    for(coor_y=0; coor_y<480; coor_y++) {
        ptr = ( unsigned short*)fb_mapped + screen_width * coor_y;
        for ( coor_x = 0; coor_x < 800; coor_x++)
        {
            *ptr++ = 0x0000; //black
        }
    }
    
    // color bar
    for ( coor_y = 0; coor_y < 300; coor_y++)
    {
        ptr = ( unsigned short*)fb_mapped + screen_width * coor_y;
        for ( coor_x = 0; coor_x < 114; coor_x++)
        {
            *ptr++ = 0xffff; //white
        }
        
        for ( coor_x = 114; coor_x < 114*2; coor_x++)
        {
            *ptr++ = 0xffe0; //yellow
        }
        
            for ( coor_x = (114*2); coor_x < 114*3; coor_x++)
        {
            *ptr++ = 0x07ff; //aqua
        }
        
        for ( coor_x = (114*3); coor_x < 114*4; coor_x++)
        {
            *ptr++ = 0x07e0; //green
        }

        for ( coor_x = (114*4); coor_x < 114*5; coor_x++)
        {
            *ptr++ = 0xf81f; //magenta
        }
        
        for ( coor_x = (114*5); coor_x < 114*6; coor_x++)
        {
            *ptr++ = 0xf800; //red
        }
        for ( coor_x = (114*6+1); coor_x < (114*7-2); coor_x++)
        {
            *ptr++ = 0x001f; //blue
        }
    }
    for ( coor_y = 300; coor_y < 350; coor_y++)
    {
        ptr = ( unsigned short*)fb_mapped + screen_width * coor_y;
        for ( coor_x = 0; coor_x < 114; coor_x++)
        {
            *ptr++ = 0x001f; //blue
        }
        
        for ( coor_x = 114; coor_x < 114*2; coor_x++)
        {
            *ptr++ = 0x0000; //black
        }
        
        for ( coor_x = (114*2); coor_x < 114*3; coor_x++)
        {
            *ptr++ = 0xf81f; //magenta
        }
        
        for ( coor_x = (114*3); coor_x < 114*4; coor_x++)
        {
            *ptr++ = 0x0000; //black
        }
        
        for ( coor_x = (114*4); coor_x < 114*5; coor_x++)
        {
            *ptr++ = 0x07ff; //aqua
        }
        
        for ( coor_x = (114*5); coor_x < 114*6; coor_x++)
        {
            *ptr++ = 0x0000; //black
        }
        
        for ( coor_x = (114*6+1); coor_x < (114*7-2); coor_x++)
        {
            *ptr++ = 0xffff; //white
        }
    }
    for ( coor_y = 350; coor_y < 480; coor_y++)
    {
        ptr = ( unsigned short*)fb_mapped + screen_width * coor_y;
        for ( coor_x = 0; coor_x < 130; coor_x++)
        {
            *ptr++ = 0x0010; //navy
        }
        
        for ( coor_x = 130; coor_x < 130*2; coor_x++)
        {
            *ptr++ = 0xffff; //white
        }
        
        for ( coor_x = 130*2; coor_x < 130*3; coor_x++)
        {
            *ptr++ = 0x0011; //darkblue
        }
        for ( coor_x = 130*3; coor_x < 800; coor_x++)
        {
            *ptr++ = 0x0000; //black
        }
    }
    munmap( fb_mapped, mem_size);
    close( fb_fd);
    
    return 0;
}