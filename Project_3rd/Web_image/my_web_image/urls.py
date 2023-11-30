# urls.py

from django.urls import path
from .views import upload_image
from django.conf import settings
from django.conf.urls.static import static

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

urlpatterns = [
    path('upload/', upload_image, name='upload_image'),
    # 다른 URL 패턴들을 추가할 수 있습니다.
]
