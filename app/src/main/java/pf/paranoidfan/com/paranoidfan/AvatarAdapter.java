package pf.paranoidfan.com.paranoidfan;

import android.content.Context;

import java.util.List;

import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselItem;

/**
 * Created by KasperStar on 9/11/2016.
 */
public final class AvatarAdapter
        extends CarouselAdapter<String>
{

    public AvatarAdapter(Context context, List<String> photos)
    {
        super(context, photos);
    }

    @Override
    public CarouselItem<String> getCarouselItem(Context context)
    {
        return new PhotoItem(context);
    }

}
