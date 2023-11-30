# views.py

from django.shortcuts import render, redirect
from .forms import ImageUploadForm
from .models import Image

def upload_image(request):
    if request.method == 'POST':
        form = ImageUploadForm(request.POST, request.FILES)
        if form.is_valid():
            form.save()
            return redirect('upload_success')  # 이동할 URL을 지정하세요
    else:
        form = ImageUploadForm()
    return render(request, 'upload_image.html', {'form': form})
