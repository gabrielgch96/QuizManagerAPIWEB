import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { QuestionFormComponent } from './components/question/question-form/question-form.component';
import { QuestionListComponent } from './components/question/question-list/question-list.component';
import { HttpClientModule} from '@angular/common/http';
import { DashboardComponent } from './dashboard/dashboard.component';
import { OptionListComponent } from './components/option/option-list/option-list.component';
import { OptionFormComponent } from './components/option/option-form/option-form.component';
import { LoginComponent } from './components/login/login.component';
import { UserFormComponent } from './components/user/user-form/user-form.component';
import { AppRoutingModule } from './app-routing.module';
import { UsersListComponent } from './components/user/users-list/users-list.component';
import { ExamsListComponent } from './components/exam/exams-list/exams-list.component';
import { ExamFormComponent } from './components/exam/exam-form/exam-form.component';
import { UserDetailComponent } from './components/user/user-detail/user-detail.component';
import { TopicFormComponent } from './components/topic/topic-form/topic-form.component';
import { TopicListComponent } from './components/topic/topic-list/topic-list.component';
import { TopicDetailComponent } from './components/topic/topic-detail/topic-detail.component';
import { QuestionDetailComponent } from './components/question/question-detail/question-detail.component';
import { ExamDetailComponent } from './components/exam/exam-detail/exam-detail.component';
import { QuestionSearchComponent } from './components/question/question-search/question-search.component';
import { TakeExamComponent } from './components/exam/take-exam/take-exam.component';
import { SubmissionListComponent } from './components/submission/submission-list/submission-list.component';
import { SubmissionDetailComponent } from './components/submission/submission-detail/submission-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    QuestionFormComponent,
    QuestionListComponent,
    DashboardComponent,
    OptionListComponent,
    OptionFormComponent,
    LoginComponent,
    UserFormComponent,
    UsersListComponent,
    ExamsListComponent,
    ExamFormComponent,
    UserDetailComponent,
    TopicFormComponent,
    TopicListComponent,
    TopicDetailComponent,
    QuestionDetailComponent,
    ExamDetailComponent,
    QuestionSearchComponent,
    TakeExamComponent,
    SubmissionListComponent,
    SubmissionDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
